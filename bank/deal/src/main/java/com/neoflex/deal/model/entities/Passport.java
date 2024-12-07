package com.neoflex.deal.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "passport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Schema(description = "Сущноость- паспортные данные.")
public class Passport implements Serializable {

    @Id
    @Column(name = "passport_uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UUID")
    private UUID passportId;

    @Column(name = "series")
    @Schema(description = "серия паспорта")
    private String passportSeries;

    @Column(name = "number")
    @Schema(description = "номер паспорта")
    private String passportNumber;

    @Column(name = "issue_branch")
    @Schema(description = "место, где выдан паспорт")
    private String passportIssueBranch;

    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "дата выдачи паспорта", examples = "2005-10-25")
    private LocalDate passportIssueDate;


}
