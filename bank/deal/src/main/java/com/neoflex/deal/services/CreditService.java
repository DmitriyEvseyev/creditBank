package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.CreditDto;
import com.neoflex.deal.model.entities.Credit;
import com.neoflex.deal.model.enumFilds.CreditStatusEnum;
import com.neoflex.deal.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;
    private final ModelMapper modelMapper;

    public Credit createCredit(CreditDto creditDto) {
        Credit credit = modelMapper.map(creditDto, Credit.class);
        credit.setCreditStatusEnum(CreditStatusEnum.CALCULATED);

        Credit creditSave = creditRepository.save(credit);
        return creditSave;
    }
}
