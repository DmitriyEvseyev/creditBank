package com.neoflex.calculator.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.calculator.model.enumDto.GenderEnum;
import com.neoflex.calculator.model.enumDto.MaritalStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность Оценочные данные")
public class ScoringDataDto implements Serializable {

    @Schema(description = "сумма кредита")
    @NotNull(message = "Loan amount cannot be null")
    @Positive(message = "Loan amount  must be greater than zero ")
    private BigDecimal amount;

    @Schema(description = "срок кредита, мес")
    @NotNull(message = "Term amount cannot be null")
    @Positive(message = "Loan amount  must be greater than zero ")
    private Integer term;

    @Schema(description = "имя")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters.")
    private String firstName;

    @Schema(description = "фамилия")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters.")
    private String lastName;

    @Schema(description = "отчество")
    @Nullable
    private String middleName;

    @Schema(description = "пол")
    @NotNull(message = "Gender amount cannot be null")
    private GenderEnum gender;

    @Schema(description = "дата рождения", examples = "1995-10-25")
    @NotNull(message = "birthdate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Schema(description = "серия паспорта")
    @Size(min = 4, max = 4, message = "PassportSeries should be 4 characters.")
    @Positive(message = "passportSeries  must be greater than zero ")
    private String passportSeries;

    @Schema(description = "номер паспорта")
    @Size(min = 6, max = 6, message = "PassportNumber should be 6 characters.")
    @Positive(message = "passportNumber  must be greater than zero ")
    private String passportNumber;

    @Schema(description = "дата выдачи паспорта", examples = "2005-10-25")
    @NotNull(message = "passportIssueDate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @Schema(description = "место, где выдан паспорт")
    @Size(min = 2, max = 30, message = "PassportIssueBranch should be between 5 and 50 characters.")
    private String passportIssueBranch;

    @Schema(description = "семейное положение")
    @NotNull(message = "maritalStatus amount cannot be null")
    private MaritalStatusEnum maritalStatus;

    @Schema(description = "зависимая сумма")
    @NotNull(message = "DependentAmount cannot be null")
    @Positive(message = "dependentAmount  must be greater than zero ")
    private Integer dependentAmount;

    @Schema(description = "Данные о трудоустройстве.")
    @NotNull(message = "Employment cannot be null")
    private EmploymentDto employment;

    @Schema(description = "номер счета")
    @Size(min = 20, max = 20, message = "accountNumber should be 20 characters.")
    private String accountNumber;

    @Schema(description = "страховка")
    @NotNull(message = "IsInsuranceEnabled cannot be null")
    private Boolean isInsuranceEnabled;

    @Schema(description = "зарплатный клиент")
    @NotNull(message = "IsSalaryClient cannot be null")
    private Boolean isSalaryClient;

}