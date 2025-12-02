package com.projectweb.marktplace.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para atualizar uma mensagem")
public record UpdateMessageRequest(
        @Schema(description = "Conteúdo da mensagem", example = "Mensagem atualizada") @NotBlank(message = "O conteúdo é obrigatório") @Size(min = 1, max = 1000, message = "O conteúdo deve ter entre 1 e 1000 caracteres") String content) {
}
