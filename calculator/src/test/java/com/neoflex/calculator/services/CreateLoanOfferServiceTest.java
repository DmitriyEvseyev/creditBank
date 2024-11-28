package com.neoflex.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateLoanOfferServiceTest {

    private CreateLoanOfferService createLoanOfferService;

    @BeforeEach
    void setUp() {
        createLoanOfferService = new CreateLoanOfferService();

        createLoanOfferService.setAnnualInterestRate(BigDecimal.valueOf(23));
        createLoanOfferService.setInterestrateIsInsuranceEnabled(BigDecimal.valueOf(2));
        createLoanOfferService.setInterestrateIsSalaryClient(BigDecimal.valueOf(1));
        createLoanOfferService.setInsurance(BigDecimal.valueOf(50000));
    }

    @Test
    public void generateUUID() {
        UUID uuid = createLoanOfferService.generateUUID();
        assertNotNull(uuid);
    }

    @Test
    public void testGetTotalAmountWithoutInsuranceAndSalaryClient() {
        BigDecimal requestedAmount = new BigDecimal("10000");
        Integer term = 12;
        Boolean isInsuranceEnabled = false;
        Boolean isSalaryClient = false;

        BigDecimal totalAmount = createLoanOfferService.getTotalAmount(
                requestedAmount,
                term,
                isInsuranceEnabled,
                isSalaryClient);

        // Ожидаемый результат: monthlyPayment * term + 0
        BigDecimal expectedMonthlyPayment = createLoanOfferService
                .getMonthlyPayment(requestedAmount, term, isInsuranceEnabled, isSalaryClient);

        assertEquals(expectedMonthlyPayment.multiply(new BigDecimal(term)), totalAmount);

    }

    @Test
    public void testGetTotalAmountWithInsurance() {
        BigDecimal requestedAmount = new BigDecimal("10000");
        Integer term = 12;
        Boolean isInsuranceEnabled = true;
        Boolean isSalaryClient = false;

        BigDecimal totalAmount = createLoanOfferService.getTotalAmount(requestedAmount, term, isInsuranceEnabled, isSalaryClient);

        // Ожидаемый результат: monthlyPayment * term + insurance
        BigDecimal expectedMonthlyPayment = createLoanOfferService.getMonthlyPayment(requestedAmount, term, isInsuranceEnabled, isSalaryClient);
        assertEquals(expectedMonthlyPayment.multiply(new BigDecimal(term)).add(createLoanOfferService.getInsurance()), totalAmount);
    }

    @Test
    public void testAnnualInterestRateCalculate() {
        assertEquals(new BigDecimal("20"), createLoanOfferService.annualInterestRateCalculate(true, true));
        assertEquals(new BigDecimal("21"), createLoanOfferService.annualInterestRateCalculate(true, false));
        assertEquals(new BigDecimal("22"), createLoanOfferService.annualInterestRateCalculate(false, true));
        assertEquals(new BigDecimal("23"), createLoanOfferService.annualInterestRateCalculate(false, false));
    }

    @Test
    public void getMonthlyPayment() {
        BigDecimal requestedAmount = new BigDecimal("10000");
        Integer term = 12;
        Boolean isInsuranceEnabled = false;
        Boolean isSalaryClient = false;

        BigDecimal monthlyPayment = createLoanOfferService.getMonthlyPayment(requestedAmount, term, isInsuranceEnabled, isSalaryClient);

        assertNotNull(monthlyPayment);
        assertTrue(monthlyPayment.compareTo(BigDecimal.ZERO) > 0);
    }
}