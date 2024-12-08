package com.neoflex.statement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность Кредитное предложение")
public class LoanOfferDto implements Serializable {

    @Schema(description = "UUID")
    @NotNull(message = "UUID cannot be null")
    private UUID uuid;

    @Schema(description = "запрашиваемая кредита")
    @NotNull(message = "requestedAmount cannot be null")
    @Positive(message = "requestedAmount  must be greater than zero ")
    private BigDecimal requestedAmount;

    @Schema(description = "общая сумма кредита")
    @NotNull(message = "requestedAmount cannot be null")
    @Positive(message = "requestedAmount  must be greater than zero ")
    private BigDecimal totalAmount;

    @Schema(description = "срок кредита, мес.")
    @NotNull(message = "Term cannot be null")
    @Positive(message = "Term  must be greater than zero " )
    private Integer term;

    @Schema(description = "ежемесячный платеж")
    @NotNull(message = "MonthlyPayment cannot be null")
    @Positive(message = "monthlyPayment must be greater than zero ")
    private BigDecimal monthlyPayment;

    @Schema(description = "процентная ставка")
    @NotNull(message = "Rate cannot be null")
    @Positive(message = "rate must be greater than zero ")
    private BigDecimal rate;

    @Schema(description = "страховка")
    @NotNull(message = "IsInsuranceEnabled cannot be null")
    private Boolean isInsuranceEnabled;

    @Schema(description = "зарплатный клиент")
    @NotNull(message = "IsSalaryClient cannot be null")
    private Boolean isSalaryClient;
}

