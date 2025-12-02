package com.projectweb.marktplace.dto.rating;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Requisição para atualizar uma avaliação")
public record UpdateRatingRequest(
        @Schema(description = "Pontuação da avaliação (1-5)", example = "4") @NotNull(message = "A pontuação é obrigatória") @Min(value = 1, message = "A pontuação mínima é 1") @Max(value = 5, message = "A pontuação máxima é 5") Integer score) {
}
