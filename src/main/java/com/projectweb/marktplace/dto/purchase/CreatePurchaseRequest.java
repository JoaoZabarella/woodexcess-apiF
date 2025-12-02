package com.projectweb.marktplace.dto.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Requisição para criar uma nova compra")
public record CreatePurchaseRequest(
        @Schema(description = "ID do anúncio a ser comprado", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O ID do anúncio é obrigatório") UUID adId) {
}
