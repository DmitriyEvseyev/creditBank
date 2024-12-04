package com.neoflex.deal.model.entities;

import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

@Schema(description = "Сущность - заявление.")
public class Statement {
    @Id
    @Column(name = "statement_uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UUID")
    private UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Schema(description = "статус заявки")
    private ApplicationStatusEnum applicationStatusEnum;

    @Column(name = "creation_date")
    @Schema(description = "дата создания")
    private Timestamp creationDate;

    @Column(name = "applied_offer")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "выбранное предложение")
    private LoanOfferDto loanOfferDto;

    @Column(name = "sing_date")
    @Schema(description = "дата исполнения")
    private Timestamp singDate;

    @Column(name = "ses_code")
    @Schema(description = "код SES")
    private String sesCode;

    @Column(name = "status_history")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "состояние истории")
    private List<StatusHistory> listStatusHistory;

    @Override
    public String toString() {
        UUID clientId = null;
        if (client != null) clientId = client.getClientId();
        UUID creditId = null;
        if (credit != null) creditId = credit.getCreditId();
        return "Statement{" +
                "statementId=" + statementId +
                ", client=" + clientId +
                ", credit=" + creditId +
                ", applicationStatusEnum=" + applicationStatusEnum +
                ", creationDate=" + creationDate +
                ", loanOfferDto=" + loanOfferDto +
                ", singDate=" + singDate +
                ", sesCode='" + sesCode + '\'' +
                ", listStatusHistory=" + listStatusHistory +
                '}';
    }
}
