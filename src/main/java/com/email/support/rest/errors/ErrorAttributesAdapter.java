package com.email.support.rest.errors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import me.alidg.errors.HttpError;
import me.alidg.errors.HttpError.CodedMessage;
import me.alidg.errors.adapter.HttpErrorAttributesAdapter;
import me.alidg.errors.conf.ErrorsProperties.ArgumentExposure;

/**
 * 
 * Implementation of {@link HttpErrorAttributesAdapter} which converts the given
 * {@link HttpError} to a {@link Map} like following:
 * <pre>
 *     {
 *         "errors": [
 *              {
 *                  "code": "the_code",
 *                  "message": "the_message",
 *                  "arguments": { // errors.expose-arguments = NOT_EMPTY or ALWAYS
 *                      "name": "value"
 *                  },
 *                  "cause": "original exception message" // Only if not a BeanValidation or ConstraintViolation
 *              }, ...
 *         ],
 *         "fingerprint": "value",	// errors.add-fingerprint = true
 *         "exception": "exception class name", // server.error.include-exception = true
 *         "status": "http status string" 
 *     }
 * </pre>
 *
 * @author Satya
 */
@Component
public class ErrorAttributesAdapter implements HttpErrorAttributesAdapter {

	@Value("${server.error.include-exception:false}")
	private boolean includeException;
	
	@Value("${errors.add-fingerprint:false}")
	private boolean hasFingerprint;

	@Value("${errors.expose-arguments:NEVER}")
	private ArgumentExposure argumentExposure;

	@Override
	public Map<String, Object> adapt(HttpError httpError) {
		
        return httpError.getErrors().stream()
        		.map(codedMessage -> this.toCodedMessageMap(codedMessage, httpError))
                .collect(collectingAndThen(
                    toList(),
                    codedMessageList -> this.toErrorAttributes(httpError, codedMessageList)
                ));
	}
	
	private boolean isBeanValidationExcepttion(Throwable exception) {
		return exception instanceof BindingResult 
				|| exception instanceof MethodArgumentNotValidException 
				|| exception instanceof ConstraintViolationException;		
	}
	
    private Map<String, Object> toCodedMessageMap(CodedMessage codedMessage, HttpError httpError) {
        Map<String, Object> codedMessageMap = new HashMap<>();
        
        codedMessageMap.put("code", codedMessage.getCode());
        codedMessageMap.put("message", codedMessage.getMessage());
        
        // Strategy of ArgumentExposure property determines whether to append arguments to the CodedMessage
        this.argumentExposure.expose(codedMessageMap, codedMessage.getArguments());

        // Add a cause if:
        // 1 - This is a Not a BeanValidating type of error
        // 2 - Not a 5xx Internal Server Error
        // 3 - exception message not equal to NULL
        Throwable exception = httpError.getRefinedException();
        String cause = exception.getLocalizedMessage();
        if (!httpError.getHttpStatus().is5xxServerError() 
        		&& !this.isBeanValidationExcepttion(exception) 
        		&& StringUtils.hasText(cause)
        ) {
        	codedMessageMap.put("cause", cause);
		}

        return codedMessageMap;
    }

    private Map<String, Object> toErrorAttributes(HttpError httpError, List<Map<String, Object>> codedMessageMap) {
        Map<String, Object> output = new HashMap<String, Object>();
        
        output.put("status", httpError.getHttpStatus().getReasonPhrase());
		
        // If fingerprint enabled, add it to the output
        if (this.hasFingerprint) {
			output.put("fingerprint", httpError.getFingerprint());
		}
        // If include exception enabled, add it to the output
		if (this.includeException) {
			output.put("exception", httpError.getOriginalException().getClass().getName());
		}
        
        output.put("errors", codedMessageMap);

        return output;
    }
}
