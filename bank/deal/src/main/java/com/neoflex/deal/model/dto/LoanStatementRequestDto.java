package com.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Positive(message = "Loan amount  must be greater than zero " )
    private BigDecimal amount;

    @Schema(description = "срок кредита, мес.")
    @NotNull(message = "Term cannot be null")
    @Positive(message = "Term  must be greater than zero " )
    private Integer term;

    @Schema(description = "имя")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters.")
    private String firstName;

    @Schema(description = "фамилия")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters.")
    private String lastName;

    @Schema(description = "отчество")
    @Nullable
    private String middleName;

    @Schema(description = "электронная почта")
    @NotNull(message = "email cannot be null")
    @Email (message = "invalid email format")
    private String email;

    @Schema(description = "дата рождения", examples = "1995-10-25")
    @NotNull(message = "birthdate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Schema(description = "серия паспорта")
    @Size(min = 4, max = 4, message = "PassportSeries should be 4 characters.")
    @Positive(message = "PassportSeries  must be greater than zero " )
    private String passportSeries;

    @Schema(description = "номер паспорта")
    @Size(min = 6, max = 6, message = "PassportNumber should be 6 characters.")
    @Positive(message = "PassportNumber  must be greater than zero " )
    private String passportNumber;
}
