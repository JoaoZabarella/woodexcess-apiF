package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.ad.AdResponse;
import com.projectweb.marktplace.dto.ad.CreateAdRequest;
import com.projectweb.marktplace.dto.ad.UpdateAdRequest;
import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.service.AdService;
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
@RequestMapping("/api/ads")
@Tag(name = "Anúncios", description = "Endpoints para gerenciamento de anúncios")
@SecurityRequirement(name = "bearerAuth")
public class AdController {

    private final AdService service;

    @Autowired
    public AdController(AdService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os anúncios", description = "Retorna uma lista com todos os anúncios cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de anúncios retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<AdResponse> getAll() {
        return service.listAll();
    }

    @Operation(summary = "Buscar anúncio por ID", description = "Retorna os dados de um anúncio específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anúncio encontrado", content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "404", description = "Anúncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdResponse> getById(
            @Parameter(description = "ID do anúncio", required = true) @PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar novo anúncio", description = "Cadastra um novo anúncio no sistema. O usuário é obtido automaticamente do token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anúncio criado com sucesso", content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AdResponse> create(@Valid @RequestBody CreateAdRequest request) {
        AdResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Atualizar anúncio", description = "Atualiza os dados de um anúncio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anúncio atualizado com sucesso", content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "404", description = "Anúncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdResponse> update(
            @Parameter(description = "ID do anúncio", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateAdRequest request) {
        AdResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar anúncio", description = "Remove um anúncio do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anúncio deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anúncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do anúncio", required = true) @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
