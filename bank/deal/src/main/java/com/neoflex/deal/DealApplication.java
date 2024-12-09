package com.neoflex.deal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DealApplication {
    public static void main(String[] args) {
        SpringApplication.run(DealApplication.class, args);
        log.info("Start!");
    }
}
