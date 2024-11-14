package com.neoflex.calculator.model.dto;
import jakarta.validation.constraints.NotNull;
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
    private BigDecimal amount;

    @NotNull(message = "Term cannot be null")
    private Integer term;

    @NotNull(message = "MonthlyPayment cannot be null")
    private BigDecimal monthlyPayment;

    @NotNull(message = "Rate cannot be null")
    private BigDecimal rate;

    @NotNull(message = "Psk cannot be null")
    private BigDecimal psk; // Полная стоимость кредита

    @NotNull(message = "Psk cannot be null")
    private Boolean isInsuranceEnabled; // Включена ли страховка

    @NotNull(message = "isSalaryClient cannot be null")
    private Boolean isSalaryClient; // Является ли клиент зарплатным

    @NotNull(message = "List<PaymentScheduleElementDto> cannot be null")
    private List<PaymentScheduleElementDto> paymentSchedule; // График платежей

}
