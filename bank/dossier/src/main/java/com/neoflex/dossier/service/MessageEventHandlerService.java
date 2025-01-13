package com.neoflex.dossier.service;

import com.neoflex.core.dto.EmailMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageEventHandlerService {

    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(MessageEventHandlerService.class);

    @Autowired
    public MessageEventHandlerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "finish_registration")
    public void handleFinishRegistration(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
    @KafkaListener(topics = "create_documents")
    public void handleCreateDocuments(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
    @KafkaListener(topics = "send_documents")
    public void handleSendDocuments(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
    @KafkaListener(topics = "send_ses")
    public void handleSendSES(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
    @KafkaListener(topics = "credit_issued")
    public void handleCreditIssued(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
    @KafkaListener(topics = "statement_denied")
    public void handleStatementDenied(EmailMessageDto emailMessageDto) {
        log.info("emailMessageDto - {}", emailMessageDto);
        emailService.sendSimpleEmail(emailMessageDto);
    }
}
