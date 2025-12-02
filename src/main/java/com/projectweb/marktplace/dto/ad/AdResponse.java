package com.projectweb.marktplace.dto.ad;

import com.projectweb.marktplace.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Resposta contendo dados de um anúncio")
public record AdResponse(
        @Schema(description = "ID único do anúncio", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,

        @Schema(description = "Título do anúncio", example = "Vendo madeira de qualidade") String title,

        @Schema(description = "Dados do usuário que criou o anúncio") UserResponse user,

        @Schema(description = "Lista de IDs das imagens do anúncio") List<UUID> imageIds) {
}
