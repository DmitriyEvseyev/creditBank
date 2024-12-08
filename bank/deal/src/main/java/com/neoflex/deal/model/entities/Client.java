package com.neoflex.deal.model.entities;

import com.neoflex.deal.model.enumFilds.GenderEnum;
import com.neoflex.deal.model.enumFilds.MaritalStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Schema(description = "Сущноость - Клиент.")
public class Client {
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UUID")
    private UUID clientId;

    @Column(name = "last_name")
    @Schema(description = "фамилия")
    private String lastName;

    @Column(name = "first_name")
    @Schema(description = "имя")
    private String firstName;

    @Column(name = "middle_name")
    @Schema(description = "отчество")
    private String middleName;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "дата рождения", examples = "1995-10-25")
    private LocalDate birthdate;

    @Column(name = "email")
    @Schema(description = "электронная почта")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @Schema(description = "пол")
    private GenderEnum gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    @Schema(description = "семейное положение")
    private MaritalStatusEnum maritalStatus;

    @Column(name = "dependent_amount")
    @Schema(description = "сумма кредита")
    private Integer dependentAmount;

    @Column(name = "passport_id")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "passport UUID")
    private Passport passport;

    @Column(name = "employment_id")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "employment UUID")
    private Employment employment;

    @Column(name = "account_number")
    @Schema(description = "номер счета")
    private String accountNumber;

    @OneToOne(mappedBy = "client", cascade = CascadeType.REMOVE)
    private Statement statement;

}

