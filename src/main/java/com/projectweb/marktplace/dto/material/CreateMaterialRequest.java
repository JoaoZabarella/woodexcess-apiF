package com.projectweb.marktplace.dto.material;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para criar um novo material")
public record CreateMaterialRequest(
        @Schema(description = "Tipo do material", example = "Madeira", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O tipo do material é obrigatório") @Size(min = 2, max = 100, message = "O tipo deve ter entre 2 e 100 caracteres") String type) {
}
