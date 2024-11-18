package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculatorOffersServiceTest {

    @Mock
    private CreateLoanOfferService createOffer;

    @InjectMocks
    private CalculatorOffersService calculatorOffersService;

    private LoanStatementRequestDto loanStatementRequestDto;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testCalculateOffers() {
    }
}