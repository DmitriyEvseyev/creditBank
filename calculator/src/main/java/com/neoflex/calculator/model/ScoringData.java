package com.neoflex.calculator.model;

import com.neoflex.calculator.model.dto.EmploymentDto;
import com.neoflex.calculator.model.enam.GenderEnam;
import com.neoflex.calculator.model.enam.MaritalStatusEnam;
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
public class ScoringData {
    private BigDecimal amount;
    private Integer term;
    private String firstName;
    private String lastName;
    private String middleName;
    private GenderEnam gender;
    private LocalDate birthdate;
    private String passportSeries;
    private String passportNumber;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private MaritalStatusEnam maritalStatus; // семья
    private Integer dependentAmount;
    private EmploymentDto employment;
    private String accountNumber;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

}
