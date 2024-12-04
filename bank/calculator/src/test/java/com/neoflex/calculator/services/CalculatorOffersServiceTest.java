package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CalculatorOffersServiceTest {
    @Mock
    CreateLoanOfferService createOffer;
    @InjectMocks
    CalculatorOffersService calculatorOffersService;
    private LoanStatementRequestDto loanStatementRequestDto;
    @BeforeEach
    public void setUp() {
        loanStatementRequestDto = new LoanStatementRequestDto();
    }
    @Test
    public void testCalculateOffers() {
        // без страховки, без зарплатного клиента
        when(createOffer.annualInterestRateCalculate(false, false)).thenReturn(new BigDecimal("15.0"));
        // без страховки, без зарплатного клиента
        when(createOffer.annualInterestRateCalculate(true, false)).thenReturn(new BigDecimal("13.0"));
        // с страховкой, без зарплатного клиента
        when(createOffer.annualInterestRateCalculate(true, true)).thenReturn(new BigDecimal("22.0"));
        // с страховкой, с зарплатным клиентом
        when(createOffer.annualInterestRateCalculate(false, true)).thenReturn(new BigDecimal("14.0"));
        // без страховки, с зарплатным клиентом

        List<LoanOfferDto> offers = calculatorOffersService.calculateOffers(loanStatementRequestDto);
        offers.stream()
                .map(LoanOfferDto::getRate)
                .forEach(System.out::println);

        assertThat(offers.size()).isEqualTo(4);

        // Проверка, что предложения отсортированы по ставке
        assertThat(offers.get(2).getRate().compareTo(offers.get(3).getRate())).isEqualTo(-1);
    }
}