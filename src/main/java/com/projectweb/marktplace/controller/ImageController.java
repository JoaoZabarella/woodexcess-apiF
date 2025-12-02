package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.dto.image.CreateImageRequest;
import com.projectweb.marktplace.dto.image.ImageResponse;
import com.projectweb.marktplace.dto.image.UpdateImageRequest;
import com.projectweb.marktplace.service.ImageService;
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
@RequestMapping("/api/images")
@Tag(name = "Imagens", description = "Endpoints para gerenciamento de imagens de anúncios")
@SecurityRequirement(name = "bearerAuth")
public class ImageController {

    private final ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as imagens", description = "Retorna uma lista com todas as imagens cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imagens retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<ImageResponse> getAll() {
        return service.listAll();
    }

    @Operation(summary = "Buscar imagem por ID", description = "Retorna os dados de uma imagem específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem encontrada"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getById(
            @Parameter(description = "ID da imagem", required = true) @PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar nova imagem", description = "Adiciona uma nova imagem a um anúncio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Anúncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ImageResponse> create(@Valid @RequestBody CreateImageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Atualizar imagem", description = "Atualiza a URL de uma imagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ImageResponse> update(
            @Parameter(description = "ID da imagem", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateImageRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Deletar imagem", description = "Remove uma imagem do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imagem deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da imagem", required = true) @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
