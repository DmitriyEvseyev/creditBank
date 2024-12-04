package com.neoflex.calculator.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность кредит")
public class CreditDto implements Serializable {

    @Schema(description = "Сумма кредита")
    @NotNull(message = "Loan amount cannot be null")
    @Positive(message = "amount must be greater than zero ")
    private BigDecimal amount;

    @Schema(description = "срок кредита, мес.")
    @NotNull(message = "Term cannot be null")
    @Positive(message = "term must be greater than zero ")
    private Integer term;

    @Schema(description = "ежемесячный платеж")
    @NotNull(message = "MonthlyPayment cannot be null")
    @Positive(message = "monthlyPayment must be greater than zero ")
    private BigDecimal monthlyPayment;

    @Schema(description = "процентная ставка")
    @NotNull(message = "Rate cannot be null")
    @Positive(message = "rate must be greater than zero ")
    private BigDecimal rate;

    @Schema(description = "Полная стоимость кредита")
    @NotNull(message = "Psk cannot be null")
    @Positive(message = "Psk must be greater than zero ")
    private BigDecimal psk;

    @Schema(description = "Включена ли страховка")
    @NotNull(message = "isInsuranceEnabled cannot be null")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Является ли клиент зарплатным")
    @NotNull(message = "isSalaryClient cannot be null")
    private Boolean isSalaryClient;

    @Schema(description = "График платежей")
    @NotEmpty(message = "List<PaymentScheduleElementDto> cannot be null")
    private List<PaymentScheduleElementDto> paymentSchedule;

}
