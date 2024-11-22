package com.neoflex.calculator.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@Data
public class CreateLoanOfferService {
    @Value("${application.bank.interestrate}")
    private BigDecimal annualInterestRate;
    @Value("${application.bank.interestrateIsInsuranceEnabled}")
    private BigDecimal interestrateIsInsuranceEnabled;
    @Value("${application.bank.interestrateIsSalaryClient}")
    private BigDecimal interestrateIsSalaryClient;
    @Value("${application.bank.insurance}")
    private BigDecimal insurance;

    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    public BigDecimal getTotalAmount(BigDecimal requestedAmount,
                                     Integer term,
                                     Boolean isInsuranceEnabled,
                                     Boolean isSalaryClient) {
        return getMonthlyPayment(requestedAmount, term, isInsuranceEnabled, isSalaryClient)
                .multiply(new BigDecimal(term))
                .add(getAmountOfIsInsuranceEnabled(isInsuranceEnabled));
    }

    // страховка
    private BigDecimal getAmountOfIsInsuranceEnabled(Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? insurance : new BigDecimal("0");
    }

    // уменьшение ставки в зависимости от isInsuranceEnabled, isInsuranceEnabled
    public BigDecimal annualInterestRateCalculate(Boolean isInsuranceEnabled,
                                                  Boolean isSalaryClient) {
        //isInsuranceEnabled  - true, isInsuranceEnabled - true
        if (isInsuranceEnabled & isSalaryClient)
            return annualInterestRate.subtract(interestrateIsInsuranceEnabled).subtract(interestrateIsSalaryClient);
        //isInsuranceEnabled  - true, isInsuranceEnabled - false
        if (isInsuranceEnabled & !isSalaryClient)
            return annualInterestRate.subtract(interestrateIsInsuranceEnabled);
        //isInsuranceEnabled  - false, isInsuranceEnabled - true
        if (!isInsuranceEnabled & isSalaryClient)
            return annualInterestRate.subtract(interestrateIsSalaryClient);
        return annualInterestRate;
    }

    public BigDecimal getMonthlyPayment(BigDecimal requestedAmount,
                                        Integer term,
                                        Boolean isInsuranceEnabled,
                                        Boolean isSalaryClient) {
        // Расчет ежемесячной процентной ставки
        BigDecimal monthlyInterestRate = annualInterestRateCalculate(isInsuranceEnabled, isSalaryClient)
                .divide(new BigDecimal("1200"), 5, RoundingMode.HALF_UP);
        // Общее количество платежей, учитывая, что term в мес.
        int totalPayments = term;
        BigDecimal numerator = requestedAmount.multiply(monthlyInterestRate).multiply(BigDecimal.ONE.add(monthlyInterestRate).pow(totalPayments));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyInterestRate).pow(totalPayments).subtract(BigDecimal.ONE);
        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        return monthlyPayment;
    }
}
