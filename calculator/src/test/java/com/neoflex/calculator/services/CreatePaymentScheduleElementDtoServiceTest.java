package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.PaymentScheduleElementDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreatePaymentScheduleElementDtoServiceTest {
    private CreatePaymentScheduleElementDtoService service;
    private ScoringDataDto dto;
    private BigDecimal rate;
    private BigDecimal monthlyPayment;

    @BeforeEach
    void setUp() {
        service = new CreatePaymentScheduleElementDtoService();
        dto = new ScoringDataDto();
        dto.setTerm(12);
        dto.setAmount(new BigDecimal("100000"));

        rate = new BigDecimal("23");
        monthlyPayment = new BigDecimal("5000");
    }

    @Test
    void createPaymentScheduleElementDto() {
        List<PaymentScheduleElementDto> dtoList = service.createPaymentScheduleElementDto(dto, rate, monthlyPayment);
        System.out.println("SIZE = " + dtoList.size());
        assertEquals(12, dtoList.size());

    }

    @Test
    void getInterestPayment() {
        BigDecimal totalPayment = new BigDecimal("50000");
        BigDecimal annualInterestRate = new BigDecimal("15");
        int i = 30;
        BigDecimal interestPayment = service.getInterestPayment(totalPayment, annualInterestRate, i);

        assertEquals(new BigDecimal("635.25"), interestPayment);
    }

    @Test
    void getDatePayment() {
        int count = 3;
        LocalDate datePayment = service.getDatePayment(count);
        assertEquals(LocalDate.parse("2025-02-28"), datePayment);
    }
}