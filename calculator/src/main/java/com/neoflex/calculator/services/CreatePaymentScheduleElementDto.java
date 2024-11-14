package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.PaymentScheduleElementDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreatePaymentScheduleElementDto {

    private final PaymentScheduleService paymentScheduleService;
    private final List<PaymentScheduleElementDto> dtoList;

    @Autowired
    public CreatePaymentScheduleElementDto(PaymentScheduleService paymentScheduleService) {
        this.paymentScheduleService = paymentScheduleService;
        this.dtoList = new ArrayList<>();
    }

    public List<PaymentScheduleElementDto> createPaymentScheduleElementDto(ScoringDataDto scoringDataDto) {
        List<PaymentScheduleElementDto> dtoList = new ArrayList<>();

        for (int i = 1; i <= scoringDataDto.getTerm() * 12; i++) {
            BigDecimal totalPayment =  getTotalPayment(scoringDataDto.getAmount(), i);


            PaymentScheduleElementDto paymentScheduleElementDto = PaymentScheduleElementDto.builder()
                    .number(i)
                    .date(paymentScheduleService.getDatePayment(i))
                    .totalPayment()
                    .interestPayment()
                    .debtPayment()
                    .remainingDebt()
                    .build();

            dtoList.add(paymentScheduleElementDto);
        }
        return dtoList;
    }

    private BigDecimal getTotalPayment (BigDecimal totalPayment, Integer count) {
        return dtoList.isEmpty()? totalPayment : dtoList.get(count).getTotalPayment();
    }
}