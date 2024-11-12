package com.neoflex.calculator.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class LoanStatementRequestDto implements Serializable {

    @NotNull(message = "Loan amount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "Term cannot be null")
    private Integer term;

    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters.")
    private String firstName;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters.")
    private String lastName;

    @Size(min = 2, max = 30, message = "MiddleName should be between 2 and 30 characters.")
    private String middleName;

    @Size(min = 2, max = 30, message = "Email should be between 4 and 40 characters.")
    private String email;

//    @DateTimeFormat(pattern = "yyyy/MM/dd")
//    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "PassportSeries should be 4 characters.")
    private String passportSeries;

    @Size(min = 6, max = 6, message = "PassportNumber should be 6 characters.")
    private String passportNumber;
}
