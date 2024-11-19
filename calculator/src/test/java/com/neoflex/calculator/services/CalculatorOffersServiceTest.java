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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CalculatorOffersServiceTest {
    @Mock
    private CreateLoanOfferService createOffer;
    @InjectMocks
    private CalculatorOffersService calculatorOffersService;
    private LoanStatementRequestDto loanStatementRequestDto;

    @BeforeEach
    public void setUp() {
        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(36)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan.ivanov@example.com")
                .birthdate(LocalDate.parse("1990-01-15"))
                .passportNumber("1234")
                .passportSeries("567890")
                .build();
    }
    @Test
    public void testCalculateOffers() {
        when(createOffer.generateUUID()).thenReturn(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873a"));
        when(createOffer.getTotalAmount(any(), any(), any(), any())).thenReturn(new BigDecimal("100000")); // без страховки, без зарплатного клиента
        when(createOffer.getMonthlyPayment(any(), any(), any(), any())).thenReturn(new BigDecimal("3666.67")); // без страховки, без зарплатного клиента
        when(createOffer.annualInterestRateCalculate(any(), any())).thenReturn(new BigDecimal("15.0")); // без страховки, без зарплатного клиента
        // без страховки, без зарплатного клиента
        LoanOfferDto loanOfferDto1 = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(new BigDecimal("100000"))
                .totalAmount(createOffer.getTotalAmount(any(), any(), any(), any()))
                .term(36)
                .monthlyPayment(createOffer.getMonthlyPayment(any(), any(), any(), any()))
                .rate(createOffer.annualInterestRateCalculate(any(), any()))
                .isInsuranceEnabled(false)
                .isSalaryClient(false).build();
        System.out.println("1 - " + loanOfferDto1);

        when(createOffer.generateUUID()).thenReturn(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873b"));
        when(createOffer.getTotalAmount(any(), any(), any(), any())).thenReturn(new BigDecimal("150000")); // с страховкой, без зарплатного клиента
        when(createOffer.getMonthlyPayment(any(), any(), any(), any())).thenReturn(new BigDecimal("3833.33")); // с страховкой, без зарплатного клиента
        when(createOffer.annualInterestRateCalculate(any(), any())).thenReturn(new BigDecimal("13.0")); // с страховкой, без зарплатного клиента
        // с страховкой, без зарплатного клиента
        LoanOfferDto loanOfferDto2 = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(new BigDecimal("100000"))
                .totalAmount(createOffer.getTotalAmount(any(), any(), any(), any()))
                .term(36)
                .monthlyPayment(createOffer.getMonthlyPayment(any(), any(), any(), any()))
                .rate(createOffer.annualInterestRateCalculate(any(), any()))
                .isInsuranceEnabled(true)
                .isSalaryClient(false).build();
        System.out.println("2 - " + loanOfferDto2);

        when(createOffer.generateUUID()).thenReturn(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873d"));
        when(createOffer.getTotalAmount(any(), any(), any(), any())).thenReturn(new BigDecimal("150000"));  // с страховкой, с зарплатным клиентом
        when(createOffer.getMonthlyPayment(any(), any(), any(), any())).thenReturn(new BigDecimal("4000.00"));   // с страховкой, с зарплатным клиентом
        when(createOffer.annualInterestRateCalculate(any(), any())).thenReturn(new BigDecimal("22.0"));  // с страховкой, с зарплатным клиентом
        // с страховкой, с зарплатным клиентом
        LoanOfferDto loanOfferDto4 = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(new BigDecimal("100000"))
                .totalAmount(createOffer.getTotalAmount(any(), any(), any(), any()))
                .term(36)
                .monthlyPayment(createOffer.getMonthlyPayment(any(), any(), any(), any()))
                .rate(createOffer.annualInterestRateCalculate(any(), any()))
                .isInsuranceEnabled(true)
                .isSalaryClient(true).build();
        System.out.println("4 - " + loanOfferDto4);

        when(createOffer.generateUUID()).thenReturn(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873c"));
        when(createOffer.getTotalAmount(any(), any(), any(), any())).thenReturn(new BigDecimal("100000")); // без страховки, с зарплатным клиентом
        when(createOffer.getMonthlyPayment(any(), any(), any(), any())).thenReturn(new BigDecimal("3500.00"));  // без страховки, с зарплатным клиентом
        when(createOffer.annualInterestRateCalculate(any(), any())).thenReturn(new BigDecimal("14.0"));  // без страховки, с зарплатным клиентом
        // без страховки, с зарплатным клиентом
        LoanOfferDto loanOfferDto3 = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(new BigDecimal("100000"))
                .totalAmount(createOffer.getTotalAmount(any(), any(), any(), any()))
                .term(36)
                .monthlyPayment(createOffer.getMonthlyPayment(any(), any(), any(), any()))
                .rate(createOffer.annualInterestRateCalculate(any(), any()))
                .isInsuranceEnabled(false)
                .isSalaryClient(true).build();
        System.out.println("3 - " + loanOfferDto3);

        // Act
        List<LoanOfferDto> offers = calculatorOffersService.calculateOffers(loanStatementRequestDto);
        // Assert
        assertThat(offers.size()).isEqualTo(4);
        System.out.println(offers.get(0).getRate());
        System.out.println(offers.get(1).getRate());
        System.out.println(offers.get(2).getRate());
        System.out.println(offers.get(3).getRate());

        System.out.println(offers.get(3).getRate().compareTo(offers.get(2).getRate()));
        // Проверка, что предложения отсортированы по ставке
        assertThat(offers.get(3).getRate().compareTo(offers.get(2).getRate())).isEqualTo(-1);
//        assertTrue(offers.get(1).getRate().compareTo(offers.get(2).getRate()) <= 0);
//        assertTrue(offers.get(2).getRate().compareTo(offers.get(3).getRate()) <= 0);
//
//        // Проверка значений первого предложения
//        LoanOfferDto firstOffer = offers.get(0);
//        assertEquals("uuid4", firstOffer.getUuid());
//        assertEquals(new BigDecimal("10000"), firstOffer.getRequestedAmount());
//        assertEquals(new BigDecimal("11000"), firstOffer.getTotalAmount());
//        assertEquals(12, firstOffer.getTerm());
//        assertEquals(new BigDecimal("916.67"), firstOffer.getMonthlyPayment());
//        assertEquals(new BigDecimal("10"), firstOffer.getRate());
//        assertFalse(firstOffer.isInsuranceEnabled());
//        assertTrue(firstOffer.isSalaryClient());
    }
}