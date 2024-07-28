/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.contact.scm.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.properties.from}")
    private String domainName;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(domainName);

        mailSender.send(message);
    }

}
