package com.projectweb.marktplace.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição para atualizar uma imagem")
public record UpdateImageRequest(
        @Schema(description = "URL da imagem", example = "https://example.com/new-image.jpg") @NotBlank(message = "A URL é obrigatória") String url) {
}
