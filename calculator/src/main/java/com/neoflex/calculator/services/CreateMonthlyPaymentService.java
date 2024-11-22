package com.neoflex.calculator.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class CreateMonthlyPaymentService {
    public BigDecimal getMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term) {
        // Расчет ежемесячной процентной ставки
        BigDecimal monthlyInterestRate = rate.divide(new BigDecimal("1200"), 5, RoundingMode.HALF_UP);
        // Общее количество платежей, учитывая, что term в мес.
        int totalPayments = term;
        BigDecimal numerator = amount.multiply(monthlyInterestRate).multiply(BigDecimal.ONE.add(monthlyInterestRate).pow(totalPayments));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyInterestRate).pow(totalPayments).subtract(BigDecimal.ONE);
        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        log.info("monthlyPayment - {}, term - {}", monthlyPayment, totalPayments);
        return monthlyPayment;
    }
}


