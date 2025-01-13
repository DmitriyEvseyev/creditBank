package com.neoflex.deal.config;

import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.dto.ScoringDataDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<LoanOfferDto, ScoringDataDto>() {
            @Override
            protected void configure() {
                map().setAmount(source.getRequestedAmount());
            }
        });
        return modelMapper;
    }
}
