package com.projectweb.marktplace.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo dados de uma imagem")
public record ImageResponse(
        @Schema(description = "ID único da imagem") UUID id,

        @Schema(description = "URL da imagem") String url,

        @Schema(description = "ID do anúncio ao qual a imagem pertence") UUID adId) {
}
