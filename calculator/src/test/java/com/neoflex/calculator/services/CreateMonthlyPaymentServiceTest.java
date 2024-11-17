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
        // Данные для теста
        BigDecimal amount = new BigDecimal("100000"); // Сумма кредита
        BigDecimal rate = new BigDecimal("12"); // Годовая процентная ставка
        Integer term = 36; // Срок в месяцах

        // Ожидаемый результат
        BigDecimal expectedMonthlyPayment = new BigDecimal("3322.17");

        // Выполнение метода
        BigDecimal actualMonthlyPayment = createMonthlyPaymentService.getMonthlyPayment(amount, rate, term);

        // Проверка результата
        assertEquals(expectedMonthlyPayment.setScale(2, BigDecimal.ROUND_HALF_UP), actualMonthlyPayment);
    }

    @Test
    public void testGetMonthlyPayment_Case2() {
        // Данные для теста
        BigDecimal amount = new BigDecimal("50000"); // Сумма кредита
        BigDecimal rate = new BigDecimal("10"); // Годовая процентная ставка
        Integer term = 24; // Срок в месяцах

        // Ожидаемый результат
        BigDecimal expectedMonthlyPayment = new BigDecimal("4583.33");

        // Выполнение метода
        BigDecimal actualMonthlyPayment = createMonthlyPaymentService.getMonthlyPayment(amount, rate, term);

        // Проверка результата
        assertEquals(expectedMonthlyPayment.setScale(2, BigDecimal.ROUND_HALF_UP), actualMonthlyPayment);
    }

    @Test
    public void testGetMonthlyPayment_Case3() {
        // Данные для теста
        BigDecimal amount = new BigDecimal("200000"); // Сумма кредита
        BigDecimal rate = new BigDecimal("5"); // Годовая процентная ставка
        Integer term = 60; // Срок в месяцах

        // Ожидаемый результат
        BigDecimal expectedMonthlyPayment = new BigDecimal("3773.58");

        // Выполнение метода
        BigDecimal actualMonthlyPayment = createMonthlyPaymentService.getMonthlyPayment(amount, rate, term);

        // Проверка результата
        assertEquals(expectedMonthlyPayment.setScale(2, BigDecimal.ROUND_HALF_UP), actualMonthlyPayment);
    }

    @Test
    public void testGetMonthlyPayment_ZeroAmount() {
        // Данные для теста
        BigDecimal amount = new BigDecimal("0"); // Сумма кредита
        BigDecimal rate = new BigDecimal("10"); // Годовая процентная ставка
        Integer term = 24; // Срок в месяцах

        // Ожидаемый результат
        BigDecimal expectedMonthlyPayment = new BigDecimal("0.00");

        // Выполнение метода
        BigDecimal actualMonthlyPayment = createMonthlyPaymentService.getMonthlyPayment(amount, rate, term);

        // Проверка результата
        assertEquals(expectedMonthlyPayment.setScale(2, BigDecimal.ROUND_HALF_UP), actualMonthlyPayment);
    }
}
