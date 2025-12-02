package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.dto.rating.CreateRatingRequest;
import com.projectweb.marktplace.dto.rating.RatingResponse;
import com.projectweb.marktplace.dto.rating.UpdateRatingRequest;
import com.projectweb.marktplace.service.RatingService;
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
@RequestMapping("/api/ratings")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações de compras")
@SecurityRequirement(name = "bearerAuth")
public class RatingController {

    private final RatingService service;

    @Autowired
    public RatingController(RatingService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as avaliações", description = "Retorna uma lista com todas as avaliações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<RatingResponse> getAll() {
        return service.listAll();
    }

    @Operation(summary = "Buscar avaliação por ID", description = "Retorna os dados de uma avaliação específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getById(
            @Parameter(description = "ID da avaliação", required = true) @PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar nova avaliação", description = "Cria uma nova avaliação para uma compra. O usuário é obtido automaticamente do token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<RatingResponse> create(@Valid @RequestBody CreateRatingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Atualizar avaliação", description = "Atualiza a pontuação de uma avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> update(
            @Parameter(description = "ID da avaliação", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateRatingRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Deletar avaliação", description = "Remove uma avaliação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da avaliação", required = true) @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
