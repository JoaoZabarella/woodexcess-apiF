package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.dto.purchase.CreatePurchaseRequest;
import com.projectweb.marktplace.dto.purchase.PurchaseResponse;
import com.projectweb.marktplace.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
@Tag(name = "Compras", description = "Endpoints para gerenciamento de compras")
@SecurityRequirement(name = "bearerAuth")
public class PurchaseController {

    private final PurchaseService service;

    @Autowired
    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as compras", description = "Retorna uma lista com todas as compras realizadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<PurchaseResponse> getAll() {
        return service.listAll();
    }

    @Operation(summary = "Buscar compra por ID", description = "Retorna os dados de uma compra específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra encontrada"),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getById(
            @Parameter(description = "ID da compra", required = true) @PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar nova compra", description = "Registra uma nova compra. O comprador é obtido automaticamente do token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Anúncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PurchaseResponse> create(@Valid @RequestBody CreatePurchaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Deletar compra", description = "Remove uma compra do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Compra deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da compra", required = true) @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
