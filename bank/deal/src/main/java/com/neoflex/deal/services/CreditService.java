package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.CreditDto;
import com.neoflex.deal.model.dto.ScoringDataDto;
import com.neoflex.deal.model.entities.Credit;
import com.neoflex.deal.model.enumFilds.CreditStatusEnum;
import com.neoflex.deal.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;
    private final ModelMapper modelMapper;
    private final RestClient restClient;

    public Credit createCredit(ScoringDataDto scoringDataDto) {
        var response = restClient
                .post()
                .uri("http://localhost:8080/calculator/calc")
                .contentType(APPLICATION_JSON)
                .body(scoringDataDto)
                .retrieve()
                .toEntity(CreditDto.class);
        CreditDto creditDto = response.getBody();

        Credit credit = modelMapper.map(creditDto, Credit.class);
        credit.setCreditStatusEnum(CreditStatusEnum.CALCULATED);

        Credit creditSave = creditRepository.save(credit);
        return creditSave;
    }

    public Credit updateStatusCredit(Credit credit) {
        credit.setCreditStatusEnum(CreditStatusEnum.ISSUED);
        Credit creditSave = creditRepository.save(credit);
        return creditSave;
    }
}
