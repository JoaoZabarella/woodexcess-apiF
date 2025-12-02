package com.projectweb.marktplace.dto.purchase;

import com.projectweb.marktplace.dto.ad.AdResponse;
import com.projectweb.marktplace.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo dados de uma compra")
public record PurchaseResponse(
        @Schema(description = "ID único da compra") UUID id,

        @Schema(description = "Dados do comprador") UserResponse buyer,

        @Schema(description = "Dados do anúncio comprado") AdResponse ad) {
}
