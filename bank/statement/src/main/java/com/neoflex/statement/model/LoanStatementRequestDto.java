package com.neoflex.statement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.statement.validator.Latinate;
import com.neoflex.statement.validator.Numbers;
import com.neoflex.statement.validator.PastEighteenYears;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность заявка на кредит")
public class LoanStatementRequestDto implements Serializable {

    @Schema(description = "сумма кредита")
    @NotNull(message = "Loan amount cannot be null")
    @Min(value = 20000, message = "min = 20000")
    private BigDecimal amount;

    @Schema(description = "срок кредита, мес.")
    @NotNull(message = "Term cannot be null")
    @Min(value = 6, message = "срок кредита должен быть 6 и более месяцев")
    private Integer term;

    @Schema(description = "имя")
    @Latinate
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters.")
    private String firstName;

    @Schema(description = "фамилия")
    @Latinate
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters.")
    private String lastName;

    @Schema(description = "отчество")
    @Latinate
    @Nullable
    @Size(min = 2, max = 30, message = "MiddleName should be between 2 and 30 characters.")
    private String middleName;

    @Schema(description = "электронная почта")
    @NotNull(message = "email cannot be null")
    @Email(message = "invalid email format")
    private String email;

    @Schema(description = "дата рождения", examples = "1995-10-25")
    @NotNull(message = "birthdate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastEighteenYears
    private LocalDate birthdate;

    @Schema(description = "серия паспорта")
    @Numbers
    @Size(min = 4, max = 4, message = "PassportSeries should be 4 characters.")
    private String passportSeries;

    @Schema(description = "номер паспорта")
    @Numbers
    @Size(min = 6, max = 6, message = "PassportNumber should be 6 characters.")
    private String passportNumber;
}
