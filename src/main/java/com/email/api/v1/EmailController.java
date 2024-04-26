/**
 *
 */
package com.email.api.v1;


import com.email.ApplicationConstants;
import com.email.api.v1.model.dto.EmailRequestDto;
import com.email.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import me.alidg.errors.HttpError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + "v1")
@Validated
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;


    @ApiOperation("Send email with attachments")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpError.class),
            @ApiResponse(code = 403, message = "Unauthorized", response = HttpError.class)})
    @PostMapping("/send")
    public ResponseEntity sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        try {
            log.info("Email Request: {}", emailRequestDto);
            emailService.sendEmail(emailRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body("Email Sent.");
        } catch (Exception ex) {
            log.error("Error occurred while scheduling email", ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Email Sent Failed.");
        }
    }


    @ApiOperation("Send email with attachments")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpError.class),
            @ApiResponse(code = 403, message = "Unauthorized", response = HttpError.class)})
    @GetMapping("/get")
    public ResponseEntity getEmail() {
        try {
            log.info("Get Email Request: ");
            return ResponseEntity.status(HttpStatus.OK).body("Get Message.");
        } catch (Exception ex) {
            log.error("Error occurred while scheduling email", ex);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Get Message Failed.");
        }
    }

}
