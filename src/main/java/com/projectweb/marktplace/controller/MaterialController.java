package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.dto.material.CreateMaterialRequest;
import com.projectweb.marktplace.dto.material.MaterialResponse;
import com.projectweb.marktplace.dto.material.UpdateMaterialRequest;
import com.projectweb.marktplace.service.MaterialService;
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
@RequestMapping("/api/materials")
@Tag(name = "Materiais", description = "Endpoints para gerenciamento de materiais de construção")
@SecurityRequirement(name = "bearerAuth")
public class MaterialController {

        private final MaterialService service;

        @Autowired
        public MaterialController(MaterialService service) {
                this.service = service;
        }

        @Operation(summary = "Listar todos os materiais", description = "Retorna uma lista com todos os materiais cadastrados")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de materiais retornada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public List<MaterialResponse> getAll() {
                return service.listAll();
        }

        @Operation(summary = "Buscar material por ID", description = "Retorna os dados de um material específico pelo seu ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Material encontrado", content = @Content(schema = @Schema(implementation = MaterialResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Material não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<MaterialResponse> getById(
                        @Parameter(description = "ID do material", required = true) @PathVariable UUID id) {
                return service.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Criar novo material", description = "Cadastra um novo material no sistema")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Material criado com sucesso", content = @Content(schema = @Schema(implementation = MaterialResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<MaterialResponse> create(@Valid @RequestBody CreateMaterialRequest request) {
                MaterialResponse created = service.create(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }

        @Operation(summary = "Atualizar material", description = "Atualiza os dados de um material existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Material atualizado com sucesso", content = @Content(schema = @Schema(implementation = MaterialResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Material não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<MaterialResponse> update(
                        @Parameter(description = "ID do material", required = true) @PathVariable UUID id,
                        @Valid @RequestBody UpdateMaterialRequest request) {
                MaterialResponse updated = service.update(id, request);
                return ResponseEntity.ok(updated);
        }

        @Operation(summary = "Deletar material", description = "Remove um material do sistema")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Material deletado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Material não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(
                        @Parameter(description = "ID do material", required = true) @PathVariable UUID id) {
                service.delete(id);
                return ResponseEntity.noContent().build();
        }
}
