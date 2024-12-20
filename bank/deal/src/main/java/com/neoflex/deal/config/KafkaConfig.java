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
    @Value("${spring.kafka.topics}")
    private List<String> topicsList;

    @PostConstruct
    public void init() {
        log.info("Initialized topics list: {}", topicsList);
    }

    @Bean
    KafkaAdmin.NewTopics createTopics() {
        List<NewTopic> topics = new ArrayList<>();
        for (String name : topicsList) {
            topics.add(TopicBuilder.name(name)
                    .partitions(3)
                    .replicas(1)
                    .build());
            log.info("Creating topic: {}", name);
        }
        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));
    }
}
