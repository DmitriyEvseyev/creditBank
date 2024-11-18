package com.neoflex.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateMonthlyPaymentServiceTest {

    private CreateMonthlyPaymentService createMonthlyPaymentService;

    @BeforeEach
    public void setUp() {
        createMonthlyPaymentService = new CreateMonthlyPaymentService();
    }

    @Test
    public void testGetMonthlyPayment_Case1() {
        BigDecimal amount = new BigDecimal("100000");
        BigDecimal rate = new BigDecimal("12");
        Integer term = 36;

        // Ожидаемый результат
        BigDecimal expectedMonthlyPayment = new BigDecimal("3321.43");

        // Выполнение метода
        BigDecimal actualMonthlyPayment = createMonthlyPaymentService.getMonthlyPayment(amount, rate, term);

        // Проверка результата
        assertEquals(expectedMonthlyPayment, actualMonthlyPayment);
    }

    @Test
    public void testGetMonthlyPayment_Case2() {
        BigDecimal amount = new BigDecimal("300000");
        BigDecimal rate = new BigDecimal("15");
        Integer term = 12;

        // Ожидаемый результат
        BigDecimal expectedMonthlyPayment = new BigDecimal("27077.49");

        // Выполнение метода
        BigDecimal actualMonthlyPayment = createMonthlyPaymentService.getMonthlyPayment(amount, rate, term);

        // Проверка результата
        assertEquals(expectedMonthlyPayment, actualMonthlyPayment);
    }
}
