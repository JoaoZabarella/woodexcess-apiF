package com.projectweb.marktplace.dto.message;

import com.projectweb.marktplace.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo dados de uma mensagem")
public record MessageResponse(
        @Schema(description = "ID único da mensagem") UUID id,

        @Schema(description = "Dados do remetente") UserResponse sender,

        @Schema(description = "Dados do destinatário") UserResponse receiver,

        @Schema(description = "ID do anúncio relacionado") UUID adId,

        @Schema(description = "Conteúdo da mensagem") String content) {
}
