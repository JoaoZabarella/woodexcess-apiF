package com.projectweb.marktplace.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Requisição para criar uma nova imagem")
public record CreateImageRequest(
        @Schema(description = "URL da imagem", example = "https://example.com/image.jpg", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "A URL é obrigatória") String url,

        @Schema(description = "ID do anúncio ao qual a imagem pertence", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O ID do anúncio é obrigatório") UUID adId) {
}
