package com.neoflex.deal.services;

import com.neoflex.core.dto.EmailMessageDto;
import com.neoflex.core.dto.ThemeEnum;
import com.neoflex.deal.model.entities.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailKafkaService {
    @Value("${spring.kafka.topics}")
    private List<String> topicsList;

    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    public void createFinishRegistrationEmail(Statement statement) {
        String emailId = UUID.randomUUID().toString();
        EmailMessageDto email = EmailMessageDto.builder()
                .address(statement.getClient().getEmail())
                .theme(ThemeEnum.FINISH_REGISTRATION)
                .statementId(statement.getStatementId())
                .text("Complete the registration.").build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send(topicsList.get(0), emailId, email);
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
                .text("Proceed with the documents.").build();
        log.info("EmailMessageDto - {}", email);

        CompletableFuture<SendResult<String, EmailMessageDto>> future =
                kafkaTemplate.send(topicsList.get(0), emailId, email);
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


