package com.email.service.impl;


import com.email.api.v1.model.dto.EmailRequestDto;
import com.email.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;


/**
 * @author Satya Kaveti
 */


@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailRequestDto dto) {
        if (StringUtils.isNotEmpty(dto.getAttachment())) {
            log.info("Email Has Attachment ...");
            sendMailWithAttachment(dto);
        } else if (StringUtils.isNotEmpty(dto.getBody()) && dto.getBody().contains("<")) {
            log.info("Email is HTML ...");
            sendSimpleHTMLMail(dto);
        } else {
            log.info("SimpleMail");
            sendSimpleMail(dto);
        }
    }


    /**
     * This method will send compose and send the message
     */
    public void sendSimpleMail(EmailRequestDto dto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("email-service@gmail.com");
            message.setTo(dto.getTo());
            message.setCc(dto.getCc());
            message.setSentDate(new Date());
            message.setSubject(dto.getSubject());
            message.setText(dto.getBody());
            javaMailSender.send(message);
            log.info("SimpleMail Sent. Please Check "+dto.getTo()+" INBOX");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendSimpleHTMLMail(EmailRequestDto dto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("email-service@gmail.com");
            helper.setTo(dto.getTo());
            helper.setCc(dto.getCc());
            helper.setSentDate(new Date());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getBody(), true); // Set the second parameter to true to indicate HTML content
            javaMailSender.send(message);
            log.info("HTML Mail Sent. Please Check "+dto.getTo()+" INBOX");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void sendMailWithAttachment(EmailRequestDto dto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("smlcodes@gmail.com");
            mimeMessageHelper.setTo(dto.getTo());
            mimeMessageHelper.setText(dto.getBody(), dto.getBody().contains("<") ? true : false);
            mimeMessageHelper.setSubject(dto.getSubject());

            FileSystemResource fileSystemResource =
                    new FileSystemResource(new File(dto.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            javaMailSender.send(mimeMessage);
            log.info("Mail with attachment sent successfully. Please Check "+dto.getTo()+" INBOX");

        } catch (Exception e) {
            log.error("sendMailWithAttachment Failed: {}", e);
        }
    }


    public EmailRequestDto getEmailDto() {
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setTo("smlcodes@gmail.com");
        emailRequestDto.setCc("smlcodes@gmail.com");
        emailRequestDto.setSubject("Email Services Email Test");
        emailRequestDto.setBody("Noraml Text. <h1> HTML Header Text </h1>");
        emailRequestDto.setAttachment("resources/files/email.pdf");
        emailRequestDto.setFileName("Attachement.pdf");
        return emailRequestDto;
    }

}


