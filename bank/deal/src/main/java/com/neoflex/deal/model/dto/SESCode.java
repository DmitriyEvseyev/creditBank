package com.neoflex.deal.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "SESCode")
public class SESCode implements Serializable {

    @Schema(description = "SESCode")
    @NotNull(message = "code cannot be null")
    private String code;
}
