package com.projectweb.marktplace.dto.ad;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para atualizar um anúncio existente")
public record UpdateAdRequest(
        @Schema(description = "Título do anúncio", example = "Madeira de primeira qualidade") @NotBlank(message = "O título é obrigatório") @Size(min = 5, max = 200, message = "O título deve ter entre 5 e 200 caracteres") String title) {
}
