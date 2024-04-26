package com.email.api.v1.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Notification Email Model")
@Builder
public class EmailRequestDto
{


    @ApiModelProperty(notes = "SMTP Server", required = true)
    private String smtp;

    @ApiModelProperty(notes = "Email to", required = true)
    private String to;

    @ApiModelProperty(notes = "Email cc")
    private String cc;

    @ApiModelProperty(notes = "Email subject", required = true)
    private String subject;

    @ApiModelProperty(notes = "Employee body", required = true)
    private String body;


    @ApiModelProperty(notes = "Attachment File Path")
    private String attachment;

    @ApiModelProperty(notes = "Attachment File Path")
    private String fileName;


}
