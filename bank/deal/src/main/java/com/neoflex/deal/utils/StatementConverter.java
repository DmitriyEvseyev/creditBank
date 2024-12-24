package com.neoflex.deal.utils;

import com.neoflex.deal.model.dto.StatementDto;
import com.neoflex.deal.model.entities.Statement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatementConverter {
    private final ModelMapper modelMapper;

    public StatementDto statementConvert(Statement statement) {
        StatementDto statementDto = modelMapper.map(statement, StatementDto.class);
        statementDto.setClientId(statement.getClient().getClientId());
        statementDto.setCreditId(statement.getCredit().getCreditId());
        return statementDto;
    }

    public List<StatementDto> statementConvert(List<Statement> statementList) {
        List<StatementDto> statementDtoList = new ArrayList<>();
        for (Statement statement : statementList) {
            statementDtoList.add(statementConvert(statement));
        }
        return statementDtoList;
    }
}
