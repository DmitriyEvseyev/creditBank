package com.neoflex.calculator.model.dto;

import com.neoflex.calculator.model.enam.EmploymentStatusEnam;
import com.neoflex.calculator.model.enam.PositionEnam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class EmploymentDto {

    @NotNull(message = "EmploymentStatusEnam amount cannot be null")
    private EmploymentStatusEnam employmentStatus;

    @Size(min = 10, max = 12, message = "EmployerINN should be between 10 and 12 characters.")
    private String employerINN;

    @NotNull(message = "Salary cannot be null")
    private BigDecimal salary;

    @NotNull(message = "Position cannot be null")
    private PositionEnam position;

    @Positive(message = "The total work experience should be greater than zero.")
    private Integer workExperienceTotal; // месяцы

    @Positive (message = "work Experience Current should be greater than zero.")
    private Integer workExperienceCurrent; // месяцы

}
