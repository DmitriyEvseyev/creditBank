package com.neoflex.calculator.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.calculator.model.enam.GenderEnam;
import com.neoflex.calculator.model.enam.MaritalStatusEnam;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class ScoringDataDto implements Serializable {
    @NotNull(message = "Loan amount cannot be null")
    @Positive(message = "Loan amount  must be greater than zero ")
    private BigDecimal amount;

    @NotNull(message = "Term amount cannot be null")
    private Integer term; // года

    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters.")
    private String firstName;

    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters.")
    private String lastName;

    @Nullable
    private String middleName;

    @NotNull(message = "Gender amount cannot be null")
    private GenderEnam gender;

    @NotNull(message = "birthdate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "PassportSeries should be 4 characters.")
    @Positive(message = "passportSeries  must be greater than zero ")
    private String passportSeries;

    @Size(min = 6, max = 6, message = "PassportNumber should be 6 characters.")
    @Positive(message = "passportNumber  must be greater than zero ")
    private String passportNumber;

    @NotNull(message = "passportIssueDate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @Size(min = 2, max = 30, message = "PassportIssueBranch should be between 5 and 50 characters.")
    private String passportIssueBranch;

    @NotNull(message = "maritalStatus amount cannot be null")
    private MaritalStatusEnam maritalStatus; // семья

    @NotNull(message = "DependentAmount cannot be null")
    @Positive(message = "dependentAmount  must be greater than zero ")
    private Integer dependentAmount;

    @NotNull(message = "Employment cannot be null")
    private EmploymentDto employment;

    @NotNull(message = "AccountNumber cannot be null")
    private String accountNumber;

    @NotNull(message = "IsInsuranceEnabled cannot be null")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "IsSalaryClient cannot be null")
    private Boolean isSalaryClient;

}