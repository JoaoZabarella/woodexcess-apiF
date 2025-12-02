package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.dto.message.CreateMessageRequest;
import com.projectweb.marktplace.dto.message.MessageResponse;
import com.projectweb.marktplace.dto.message.UpdateMessageRequest;
import com.projectweb.marktplace.service.MessageService;
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
@RequestMapping("/api/messages")
@Tag(name = "Mensagens", description = "Endpoints para gerenciamento de mensagens entre usuários")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService service;

    @Autowired
    public MessageController(MessageService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as mensagens", description = "Retorna uma lista com todas as mensagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mensagens retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<MessageResponse> getAll() {
        return service.listAll();
    }

    @Operation(summary = "Buscar mensagem por ID", description = "Retorna os dados de uma mensagem específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem encontrada"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getById(
            @Parameter(description = "ID da mensagem", required = true) @PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar nova mensagem", description = "Envia uma nova mensagem. O remetente é obtido automaticamente do token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mensagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Destinatário ou anúncio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Atualizar mensagem", description = "Atualiza o conteúdo de uma mensagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(
            @Parameter(description = "ID da mensagem", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateMessageRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Deletar mensagem", description = "Remove uma mensagem do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mensagem deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da mensagem", required = true) @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
