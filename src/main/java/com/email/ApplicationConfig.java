package com.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import com.email.dao.entity.Employee;
//import com.email.dao.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;
import play.api.libs.mailer.SMTPConfiguration;
import play.api.libs.mailer.SMTPMailer;
import play.libs.mailer.MailerClient;

/**
 * Application-specific configuration, which may include Beans, Initializations, etc.
 */
@Configuration
@Slf4j
@ComponentScan("play.libs.mailer")
public class ApplicationConfig {

}