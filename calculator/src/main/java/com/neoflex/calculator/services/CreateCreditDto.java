package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateCreditDto {
    @Value("${application.bank.insurance}")
    private BigDecimal insurance;
    private final CreateInterestRate createInterestRate;
    private final CreateMonthlyPayment createMonthlyPayment;

    @Autowired
    public CreateCreditDto(CreateInterestRate createInterestRate, CreateMonthlyPayment createMonthlyPayment) {
        this.createInterestRate = createInterestRate;
        this.createMonthlyPayment = createMonthlyPayment;
    }

    public CreditDto createCreditDto(ScoringDataDto scoringDataDto) {
        BigDecimal rate = createInterestRate.getFinalannualInterestRate(scoringDataDto);
        BigDecimal monthlyPayment = createMonthlyPayment.getMonthlyPayment(
                scoringDataDto.getAmount(),
                rate,
                scoringDataDto.getTerm());
        BigDecimal psk = getPsk(scoringDataDto.getIsSalaryClient(), scoringDataDto.getAmount());

        CreditDto creditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule()
                .build();

        return creditDto;
    }

    //получение psk
    private BigDecimal getPsk(Boolean isInsuranceEnabled, BigDecimal amount) {
        return isInsuranceEnabled ? amount.add(insurance) : amount;
    }
}
