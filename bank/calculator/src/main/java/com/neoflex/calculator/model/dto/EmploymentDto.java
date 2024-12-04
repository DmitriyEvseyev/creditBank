package com.neoflex.calculator.model.dto;

import com.neoflex.calculator.model.enumDto.EmploymentStatusEnum;
import com.neoflex.calculator.model.enumDto.PositionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущноость Данные о трудоустройстве.")
public class EmploymentDto {

    @Schema(description = "статус занятости")
    @NotNull(message = "EmploymentStatusEnam amount cannot be null")
    private EmploymentStatusEnum employmentStatus;

    @Schema(description = "ИНН")
    @Size(min = 10, max = 12, message = "EmployerINN should be between 10 and 12 characters.")
    private String employerINN;

    @Schema(description = "зарплата")
    @NotNull(message = "Salary cannot be null")
    private BigDecimal salary;

    @Schema(description = "должность")
    @NotNull(message = "Position cannot be null")
    private PositionEnum position;

    @Schema(description = "общий стаж, в месяцах")
    @Positive(message = "The total work experience should be greater than zero.")
    private Integer workExperienceTotal;

    @Schema(description = "текущий стаж, в месяцах")
    @Positive (message = "work Experience Current should be greater than zero.")
    private Integer workExperienceCurrent;
}
