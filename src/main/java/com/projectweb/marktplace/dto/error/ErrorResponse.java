package com.projectweb.marktplace.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de erro padronizada da API")
public class ErrorResponse {

    @Schema(description = "Código de status HTTP", example = "400")
    private int status;

    @Schema(description = "Nome do status HTTP", example = "Bad Request")
    private String error;

    @Schema(description = "Mensagem de erro principal", example = "Dados inválidos fornecidos")
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro", example = "/api/users")
    private String path;

    @Schema(description = "Timestamp do erro", example = "2024-12-02T09:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Lista de erros de validação")
    private List<ValidationError> validationErrors;

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Erro de validação de campo")
    public static class ValidationError {
        @Schema(description = "Nome do campo com erro", example = "email")
        private String field;

        @Schema(description = "Mensagem de erro do campo", example = "Email inválido")
        private String message;
    }
}
