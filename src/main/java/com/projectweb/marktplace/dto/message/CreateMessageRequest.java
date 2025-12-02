package com.projectweb.marktplace.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Requisição para criar uma nova mensagem")
public record CreateMessageRequest(
        @Schema(description = "ID do destinatário", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O ID do destinatário é obrigatório") UUID receiverId,

        @Schema(description = "ID do anúncio relacionado", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O ID do anúncio é obrigatório") UUID adId,

        @Schema(description = "Conteúdo da mensagem", example = "Olá, tenho interesse no produto", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O conteúdo é obrigatório") @Size(min = 1, max = 1000, message = "O conteúdo deve ter entre 1 e 1000 caracteres") String content) {
}
