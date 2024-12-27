package com.neoflex.deal.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class KafkaConfig {

    @Bean
    public NewTopic finishRegistrationtopic() {
        return TopicBuilder.name("finish_registration")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder.name("create_documents")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name("send_documents")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder.name("send_ses")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name("credit_issued")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder.name("statement_denied")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
