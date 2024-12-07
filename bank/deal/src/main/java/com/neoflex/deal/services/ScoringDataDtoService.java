package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.dto.ScoringDataDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoringDataDtoService {
    private final ModelMapper modelMapper;

    public ScoringDataDto createScoringDataDto(FinishRegistrationRequestDto finishRegistrationRequestDto,
                                               Statement statement) {
        Client client = statement.getClient();
        log.info("client - {}", client);
        LoanOfferDto loanOfferDto = statement.getLoanOfferDto();
        log.info("loanOfferDto - {}", loanOfferDto);

        ScoringDataDto scoringDataDto = modelMapper.map(client, ScoringDataDto.class);
        modelMapper.map(finishRegistrationRequestDto, scoringDataDto);

      // Configuration configuration = modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        TypeMap<LoanOfferDto, ScoringDataDto> propertyMapper = this.modelMapper.createTypeMap(LoanOfferDto.class, ScoringDataDto.class);
//        propertyMapper.addMappings(mapper -> {mapper
//                .map(LoanOfferDto::getRequestedAmount, ScoringDataDto::setAmount);
//        });
        modelMapper.map(loanOfferDto, scoringDataDto);
      //  scoringDataDto.setAmount(loanOfferDto.getRequestedAmount());
   //    configuration =  modelMapper.getConfiguration().setAmbiguityIgnored(false);

        return scoringDataDto;
    }
}
