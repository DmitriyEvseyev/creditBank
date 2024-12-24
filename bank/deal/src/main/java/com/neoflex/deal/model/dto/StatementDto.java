package com.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.deal.model.entities.StatusHistory;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Заявка DTO")
public class StatementDto implements Serializable {
    @Schema(description = "id")
    @NotNull(message = "Id cannot be null")
    private UUID statementId;

    @Schema(description = "clientId")
    @NotNull(message = "clientId cannot be null")
    private UUID clientId;

    @Schema(description = "creditId")
    private UUID creditId;

    @Enumerated(EnumType.STRING)
    @Schema(description = "статус заявки")
    @NotNull(message = "applicationStatusEnum cannot be null")
    private ApplicationStatusEnum applicationStatusEnum;

    @Schema(description = "дата создания")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.CREATION_DATE_PATTERN)
    @NotNull(message = "creationDate cannot be null")
    private Timestamp creationDate;

    @Schema(description = "выбранное предложение")
    private LoanOfferDto loanOfferDto;

    @Schema(description = "дата исполнения")
    private Timestamp singDate;

    @Schema(description = "код SES")
    private String sesCode;

    @Schema(description = "состояние истории")
    private List<StatusHistory> listStatusHistory;
}

