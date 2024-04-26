package com.email.service;


import com.email.api.v1.model.dto.EmailRequestDto;

public interface EmailService {

    void sendEmail(EmailRequestDto emailRequestDto);
}
