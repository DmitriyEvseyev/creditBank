package com.neoflex.deal.model.entities;

import com.neoflex.deal.model.dto.PaymentScheduleElementDto;
import com.neoflex.deal.model.enumFilds.CreditStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Schema(description = "Сущность - кредит")
public class Credit {

    @Id
    @Column(name = "credit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UUID")
    private UUID creditId;

    @Column(name = "amount")
    @Schema(description = "Сумма кредита")
    private BigDecimal amount;

    @Column(name = "term")
    @Schema(description = "срок кредита, мес.")
    private Integer term;

    @Column(name = "monthly_payment")
    @Schema(description = "ежемесячный платеж")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    @Schema(description = "процентная ставка")
    private BigDecimal rate;

    @Column(name = "psk")
    @Schema(description = "Полная стоимость кредита")
    private BigDecimal psk;

    @Column(name = "payment_schedule")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "График платежей")
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Column(name = "insurance_enabled")
    @Schema(description = "Включена ли страховка")
    private Boolean isInsuranceEnabled;

    @Column(name = "salary_client")
    @Schema(description = "Является ли клиент зарплатным")
    private Boolean isSalaryClient;

    @Column(name = "credit_status")
    @Enumerated(EnumType.STRING)
    @Schema(description = "статус кредита")
    private CreditStatusEnum creditStatusEnum;

    @OneToOne(mappedBy = "credit")
    private Statement statement;

}

