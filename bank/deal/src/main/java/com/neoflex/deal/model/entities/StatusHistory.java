package com.neoflex.deal.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.model.enumFilds.ChangeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Состояние истории.")
public class StatusHistory implements Serializable {
    @Schema(description = "статус")
    private ApplicationStatusEnum status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "UTC+3")
    @Schema(description = "статус занятости")
    private Timestamp time;

    @Schema(description = "статус занятости")
    private ChangeTypeEnum changeType;

}
