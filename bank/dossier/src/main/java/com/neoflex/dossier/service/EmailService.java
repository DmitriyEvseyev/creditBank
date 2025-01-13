package com.neoflex.dossier.service;

import com.neoflex.core.dto.EmailMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendSimpleEmail(EmailMessageDto emailMessageDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessageDto.getAddress());
        message.setSubject(emailMessageDto.getTheme().toString());
        message.setText(emailMessageDto.getText() +
                " Номер заявки: " + emailMessageDto.getStatementId());
        log.info("message - {}", message);
        mailSender.send(message);
    }
}