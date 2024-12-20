package com.neoflex.dossier.service;

import com.neoflex.core.dto.EmailMessageDto;
import com.neoflex.dossier.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "finish_registration")
public class MessageEventHandlerService {

    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public MessageEventHandlerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaHandler
    public void handle(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
}
