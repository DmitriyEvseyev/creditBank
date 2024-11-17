package com.neoflex.calculator.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность График платежей")
public class PaymentScheduleElementDto {

    @NotNull(message = "Number cannot be null")
    @Positive(message = "number  must be greater than zero " )
    @Schema(description = "номер платежа")
    private Integer number;

    @NotNull(message = "date cannot be null")
    @Schema(description = "дата платежа")
    private LocalDate date;

    @Schema(description = "общая сумма платежа, которую необходимо выплатить")
    @NotNull(message = "totalPayment cannot be null")
    @Positive(message = "totalPayment  must be greater than zero " )
    private BigDecimal totalPayment;

    @NotNull(message = "interestPayment cannot be null")
    @Positive(message = "interestPayment  must be greater than zero " )
    @Schema(description = "сумма, которая идет на уплату процентов по кредиту")
    private BigDecimal interestPayment;

    @NotNull(message = "debtPayment cannot be null")
    @Positive(message = "debtPayment  must be greater than zero " )
    @Schema(description = "сумма, которая идет на погашение основного долга")
    private BigDecimal debtPayment;

    @NotNull(message = "remainingDebt cannot be null")
    @Positive(message = "remainingDebt  must be greater than zero " )
    @Schema(description = "остаток долга, который еще нужно выплатить после произведенных платежей")
    private BigDecimal remainingDebt;

}