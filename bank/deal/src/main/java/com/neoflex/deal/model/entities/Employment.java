package com.neoflex.deal.model.entities;

import com.neoflex.deal.model.enumFilds.EmploymentStatusEnum;
import com.neoflex.deal.model.enumFilds.PositionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "employment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Schema(description = "Сущность - Данные о трудоустройстве.")
public class Employment {
    @Id
    @Column(name = "employment_uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UUID")
    private UUID employmentId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Schema(description = "статус занятости")
    private EmploymentStatusEnum employmentStatus;

    @Column(name = "employer_inn")
    @Schema(description = "ИНН")
    private String employerINN;

    @Column(name = "salary")
    @Schema(description = "зарплата")
    private BigDecimal salary;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    @Schema(description = "должность")
    private PositionEnum position;

    @Column(name = "work_experience_total")
    @Schema(description = "общий стаж, в месяцах")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    @Schema(description = "текущий стаж, в месяцах")
    private Integer workExperienceCurrent;

}
