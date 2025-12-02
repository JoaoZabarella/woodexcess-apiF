package com.projectweb.marktplace.dto.rating;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Requisição para criar uma nova avaliação")
public record CreateRatingRequest(
        @Schema(description = "Pontuação da avaliação (1-5)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "A pontuação é obrigatória") @Min(value = 1, message = "A pontuação mínima é 1") @Max(value = 5, message = "A pontuação máxima é 5") Integer score,

        @Schema(description = "ID da compra sendo avaliada", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O ID da compra é obrigatório") UUID purchaseId) {
}
