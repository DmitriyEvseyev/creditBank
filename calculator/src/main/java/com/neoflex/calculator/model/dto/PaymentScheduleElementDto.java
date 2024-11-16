package com.neoflex.calculator.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive(message = "number  must be greater than zero " )
    private Integer number;

    @NotNull(message = "date cannot be null")
    private LocalDate date;

    // общая сумма платежа, которую необходимо выплатить
    @NotNull(message = "totalPayment cannot be null")
    @Positive(message = "totalPayment  must be greater than zero " )
    private BigDecimal totalPayment;

    // сумма, которая идет на уплату процентов по кредиту
    @NotNull(message = "interestPayment cannot be null")
    @Positive(message = "interestPayment  must be greater than zero " )
    private BigDecimal interestPayment;

    //сумма, которая идет на погашение основного долга
    @NotNull(message = "debtPayment cannot be null")
    @Positive(message = "debtPayment  must be greater than zero " )
    private BigDecimal debtPayment;

    // остаток долга, который еще нужно выплатить после произведенных платежей.
    @NotNull(message = "remainingDebt cannot be null")
    @Positive(message = "remainingDebt  must be greater than zero " )
    private BigDecimal remainingDebt;

}