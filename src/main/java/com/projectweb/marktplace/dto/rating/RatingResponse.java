package com.projectweb.marktplace.dto.rating;

import com.projectweb.marktplace.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo dados de uma avaliação")
public record RatingResponse(
        @Schema(description = "ID único da avaliação") UUID id,

        @Schema(description = "Pontuação (1-5)") Integer score,

        @Schema(description = "Dados do usuário que fez a avaliação") UserResponse user,

        @Schema(description = "ID da compra avaliada") UUID purchaseId) {
}
