package com.neoflex.calculator.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.calculator.model.enam.GenderEnam;
import com.neoflex.calculator.model.enam.MaritalStatusEnam;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private BigDecimal amount;

    @NotNull(message = "Term amount cannot be null")
    private Integer term;

    @NotEmpty
    private String firstName;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters.")
    private String lastName;

    @Size(min = 2, max = 30, message = "MiddleName should be between 2 and 30 characters.")
    private String middleName;

    @NotNull(message = "Gender amount cannot be null")
    private GenderEnam gender;

//    @DateTimeFormat(pattern = "yyyy/MM/dd")
//    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "PassportSeries should be 4 characters.")
    private String passportSeries;

    @Size(min = 6, max = 6, message = "PassportNumber should be 6 characters.")
    private String passportNumber;

    //    @DateTimeFormat(pattern = "yyyy/MM/dd")
//    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate passportIssueDate;

    @Size(min = 2, max = 30, message = "PassportIssueBranch should be between 5 and 50 characters.")
    private String passportIssueBranch;

    @NotNull(message = "Gender amount cannot be null")
    private MaritalStatusEnam maritalStatus; // семья

    @NotNull(message = "DependentAmount cannot be null")
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