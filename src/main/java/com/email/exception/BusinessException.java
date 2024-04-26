package com.email.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * An example of an application specific Business Exception.
 * <ol>
 * <li>Annotate with @ExceptionMapping, passing the errorCode to resolve</li>
 * <li>The statusCode field MUST be set to CONFLICT for all Business Exceptions</li>
 * <li>Utilize Lombok to auto-generate constructor() and toString() with @AllArgsConstructor and @ToString</li>
 * <li>Declare the exception arguments and annotate with @ExposeAsArgs</li>
 * <li>Create an entry in messages.properties for the errorCode defined</li>
 * </ol>
 * 
 * @author Dinesh.sharma
 */
@SuppressWarnings("serial")
@ToString
@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus httpstatus;

    public BusinessException(String errorMessage) {
        this.httpstatus=HttpStatus.INTERNAL_SERVER_ERROR;
    }


}
