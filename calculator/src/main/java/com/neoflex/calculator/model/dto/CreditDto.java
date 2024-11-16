package com.neoflex.calculator.model.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class CreditDto implements Serializable
{
    @NotNull(message = "Loan amount cannot be null")
    @Positive (message = "amount must be greater than zero " )
    private BigDecimal amount;

    @NotNull(message = "Term cannot be null")
    @Positive (message = "term must be greater than zero " )
    private Integer term; // мес.

    @NotNull(message = "MonthlyPayment cannot be null")
    @Positive (message = "monthlyPayment must be greater than zero " )
    private BigDecimal monthlyPayment;

    @NotNull(message = "Rate cannot be null")
    @Positive (message = "rate must be greater than zero " )
    private BigDecimal rate;

    @NotNull(message = "Psk cannot be null")
    @Positive (message = "Psk must be greater than zero " )
    private BigDecimal psk; // Полная стоимость кредита

    @NotNull(message = "Psk cannot be null")
    private Boolean isInsuranceEnabled; // Включена ли страховка

    @NotNull(message = "isSalaryClient cannot be null")
    private Boolean isSalaryClient; // Является ли клиент зарплатным

    @NotEmpty(message = "List<PaymentScheduleElementDto> cannot be null")
    private List<PaymentScheduleElementDto> paymentSchedule; // График платежей

}
