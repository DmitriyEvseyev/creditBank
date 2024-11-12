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
public class PaymentScheduleElementDto {
    @NotNull(message = "PaymentDate cannot be null")
    private LocalDate paymentDate; // Дата платежа

    @NotNull(message = "PaymentAmount cannot be null")
    private BigDecimal paymentAmount; // Сумма платежа

    @NotNull(message = "RemainingBalance cannot be null")
    private BigDecimal remainingBalance; // Оставшаяся задолженность
}
