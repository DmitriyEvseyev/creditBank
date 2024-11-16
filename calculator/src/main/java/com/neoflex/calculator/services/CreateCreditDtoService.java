package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.PaymentScheduleElementDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class CreateCreditDtoService {
    @Value("${application.bank.insurance}")
    private BigDecimal insurance;
    private final CreateInterestRateService createInterestRateService;
    private final CreateMonthlyPaymentService createMonthlyPaymentService;
    private final CreatePaymentScheduleElementDtoService createPaymentScheduleElementDtoService;

    @Autowired
    public CreateCreditDtoService(CreateInterestRateService createInterestRateService, CreateMonthlyPaymentService createMonthlyPaymentService, CreatePaymentScheduleElementDtoService createPaymentScheduleElementDtoService) {
        this.createInterestRateService = createInterestRateService;
        this.createMonthlyPaymentService = createMonthlyPaymentService;
        this.createPaymentScheduleElementDtoService = createPaymentScheduleElementDtoService;
    }

    public CreditDto createCreditDto(ScoringDataDto scoringDataDto) {
        BigDecimal rate = createInterestRateService.getFinalannualInterestRate(scoringDataDto);
        BigDecimal monthlyPayment = createMonthlyPaymentService.getMonthlyPayment(
                scoringDataDto.getAmount(),
                rate,
                scoringDataDto.getTerm());
        List<PaymentScheduleElementDto> paymentScheduleElementDtoList = createPaymentScheduleElementDtoService.createPaymentScheduleElementDto(
                scoringDataDto, rate, monthlyPayment);
        BigDecimal psk = getPsk(scoringDataDto.getIsInsuranceEnabled(),
                monthlyPayment,
                paymentScheduleElementDtoList.get(paymentScheduleElementDtoList.size()-1),
                scoringDataDto.getAmount());

        CreditDto creditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule(paymentScheduleElementDtoList)
                .build();
        log.info("creditDto - {}", creditDto);
        return creditDto;
    }

    //получение psk (плюс страховка)
    private BigDecimal getPsk(Boolean isInsuranceEnabled,
                              BigDecimal monthlyPayment,
                              PaymentScheduleElementDto paymentScheduleElementDto,
                              BigDecimal amount) {
        //выплаты за кредит = платеж * (кол-во платежей -1) + остаток долга и проценты последнего платежа
        BigDecimal coastLoan = monthlyPayment.multiply(new BigDecimal(paymentScheduleElementDto.getNumber()-1))
                .add(paymentScheduleElementDto.getTotalPayment())
                .add(paymentScheduleElementDto.getInterestPayment());
        // + страховка
        BigDecimal psk = isInsuranceEnabled ? coastLoan.add(insurance).subtract(amount)
                : coastLoan.subtract(amount);
        log.info("getPsk - {}", psk);
        return psk;
    }
}
