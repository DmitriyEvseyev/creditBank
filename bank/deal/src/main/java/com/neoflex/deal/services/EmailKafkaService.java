package com.neoflex.deal.services;

import com.neoflex.core.dto.EmailMessageDto;
import com.neoflex.core.dto.ThemeEnum;
import com.neoflex.deal.model.entities.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailKafkaService {

    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    public void createFinishRegistrationEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.FINISH_REGISTRATION)
                .statementId(statement.getStatementId())
                .text("Ваша заявка предварительно одобрена, завершите оформление. ").build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send("finish_registration", emailId, email);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.info("Error. {}", ex.getMessage());
            } else {
                log.info("Ok! {}", result.getRecordMetadata().topic());
                log.info("Partition -  {}", result.getRecordMetadata().partition());
            }
        });
        log.info("ID: {}", emailId);
    }

    public void createDocumentsEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.CREATE_DOCUMENTS)
                .statementId(statement.getStatementId())
                .text("Приступайте к оформлению документов. ").build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send("create_documents", emailId, email);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.info("Error. {}", ex.getMessage());
            } else {
                log.info("Ok! {}", result.getRecordMetadata().topic());
                log.info("Partition -  {}", result.getRecordMetadata().partition());
            }
        });
        log.info("ID: {}", emailId);
    }

    public void sendDocumentsEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.SEND_DOCUMENTS)
                .statementId(statement.getStatementId())
                .text("Ваши кредитные документы. ").build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send("send_documents", emailId, email);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.info("Error. {}", ex.getMessage());
            } else {
                log.info("Ok! {}", result.getRecordMetadata().topic());
                log.info("Partition -  {}", result.getRecordMetadata().partition());
            }
        });
        log.info("ID: {}", emailId);
    }

    public void singDocumentsEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.SEND_SES)
                .statementId(statement.getStatementId())
                .text("Документы на подпись. SES code: " + statement.getSesCode())
                .build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send("send_ses", emailId, email);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.info("Error. {}", ex.getMessage());
            } else {
                log.info("Ok! {}", result.getRecordMetadata().topic());
                log.info("Partition -  {}", result.getRecordMetadata().partition());
            }
        });
        log.info("ID: {}", emailId);
    }

    public void creditIssuedEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.CREDIT_ISSUED)
                .statementId(statement.getStatementId())
                .text("Кредит одобрен. ")
                .build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send("credit_issued", emailId, email);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.info("Error. {}", ex.getMessage());
            } else {
                log.info("Ok! {}", result.getRecordMetadata().topic());
                log.info("Partition -  {}", result.getRecordMetadata().partition());
            }
        });
        log.info("ID: {}", emailId);
    }

    public void deniedEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.STATEMENT_DENIED)
                .statementId(statement.getStatementId())
                .text("Вам отказано в оформлении кредит. ").build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send("statement_denied", emailId, email);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.info("Error. {}", ex.getMessage());
            } else {
                log.info("Ok! {}", result.getRecordMetadata().topic());
                log.info("Partition -  {}", result.getRecordMetadata().partition());
            }
        });
        log.info("ID: {}", emailId);
    }
}



