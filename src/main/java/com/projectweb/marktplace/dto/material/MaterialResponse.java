package com.projectweb.marktplace.dto.material;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo dados de um material")
public record MaterialResponse(
        @Schema(description = "ID único do material", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,

        @Schema(description = "Tipo do material", example = "Madeira") String type) {
}
