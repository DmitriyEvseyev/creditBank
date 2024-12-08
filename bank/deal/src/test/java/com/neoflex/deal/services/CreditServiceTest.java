package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.CreditDto;
import com.neoflex.deal.model.dto.PaymentScheduleElementDto;
import com.neoflex.deal.model.entities.Credit;
import com.neoflex.deal.model.enumFilds.CreditStatusEnum;
import com.neoflex.deal.repositories.CreditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CreditService creditService;

    private Credit credit;
    private CreditDto creditDto;
    @BeforeEach
    void setUp() {
        credit = Credit.builder()
                .creditId(UUID.randomUUID())
                .amount(new BigDecimal("150000"))
                .term(36)
                .monthlyPayment(new BigDecimal("15000"))
                .rate(new BigDecimal("9.5"))
                .psk(new BigDecimal("10.5"))
                .paymentSchedule(Arrays.asList(new PaymentScheduleElementDto()))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .creditStatusEnum(CreditStatusEnum.CALCULATED)
                .build();
        creditDto = CreditDto.builder()
                .amount(new BigDecimal("150000"))
                .term(36)
                .monthlyPayment(new BigDecimal("15000"))
                .rate(new BigDecimal("9.5"))
                .psk(new BigDecimal("10.5"))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .paymentSchedule(List.of(new PaymentScheduleElementDto()))
                .build();
    }

    @Test
    void createCredit() {
        when(modelMapper.map(creditDto, Credit.class)).thenReturn(credit);
        when(creditRepository.save(credit)).thenReturn(credit);
        Credit returnCredit = creditService.createCredit(creditDto);

        assertEquals(returnCredit, credit);
    }
}