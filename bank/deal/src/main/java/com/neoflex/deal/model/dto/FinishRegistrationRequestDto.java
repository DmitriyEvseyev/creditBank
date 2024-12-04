package com.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.deal.model.entities.Employment;
import com.neoflex.deal.model.enumFilds.GenderEnum;
import com.neoflex.deal.model.enumFilds.MaritalStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Окончательный запрос на регистрацию")
public class FinishRegistrationRequestDto implements Serializable  {
    @Schema(description = "пол")
    @NotNull(message = "Gender amount cannot be null")
    private GenderEnum gender;

    @Schema(description = "семейное положение")
    @NotNull(message = "maritalStatus amount cannot be null")
    private MaritalStatusEnum maritalStatus;

    @Schema(description = "зависимая сумма/ число иждевенцев")
    @NotNull(message = "DependentAmount cannot be null")
    @Positive(message = "dependentAmount  must be greater than zero ")
    private Integer dependentAmount;

    @Schema(description = "дата выдачи паспорта", examples = "2005-10-25")
    @NotNull(message = "passportIssueDate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @Schema(description = "место, где выдан паспорт")
    @Size(min = 2, max = 30, message = "PassportIssueBranch should be between 5 and 50 characters.")
    private String passportIssueBranch;

    @Schema(description = "Данные о трудоустройстве.")
    @NotNull(message = "Employment cannot be null")
    private Employment employment;

    @Schema(description = "номер счета")
    @Size(min = 20, max = 20, message = "accountNumber should be 20 characters.")
    private String accountNumber;
}
