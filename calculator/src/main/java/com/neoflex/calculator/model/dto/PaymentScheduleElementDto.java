package com.neoflex.calculator.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
//Элемент графика платежей
public class PaymentScheduleElementDto {
    @NotNull(message = "Number cannot be null")
    private Integer number;

    @NotNull(message = "date cannot be null")
    private LocalDate date;

    // общая сумма платежа, которую необходимо выплатить
    @NotNull(message = "totalPayment cannot be null")
    private BigDecimal totalPayment;

    // сумма, которая идет на уплату процентов по кредиту
    @NotNull(message = "interestPayment cannot be null")
    private BigDecimal interestPayment;

    //сумма, которая идет на погашение основного долга
    @NotNull(message = "debtPayment cannot be null")
    private BigDecimal debtPayment;

    // остаток долга, который еще нужно выплатить после произведенных платежей.
    @NotNull(message = "remainingDebt cannot be null")
    private BigDecimal remainingDebt;

}