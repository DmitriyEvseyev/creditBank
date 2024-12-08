package com.neoflex.deal;

import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.dto.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class DealApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealApplication.class, args);
        log.info("Start!");
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<LoanOfferDto, ScoringDataDto>() {
            @Override
            protected void configure() {
                // Указываем, что нужно использовать getRequestedAmount()
                map().setAmount(source.getRequestedAmount());
            }
        });
        return modelMapper;
    }

}
