package com.neoflex.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность - письмо")
public class EmailMessageDto implements Serializable {

    @Schema(description = "Адрес электронной почты")
    @NotNull(message = "email cannot be null")
    @Email(message = "invalid email format")
    private String address;

    @Schema(description = "Тема письма")
    @NotNull(message = "theme cannot be null")
    private ThemeEnum theme;

    @Schema(description = "номер заявки")
    @NotNull(message = "statementId cannot be null")
    private UUID statementId;

    @Schema(description = "текст письма")
    @Size(min = 2, max = 100, message = "text should be 20 characters.")
    private String text;
}

