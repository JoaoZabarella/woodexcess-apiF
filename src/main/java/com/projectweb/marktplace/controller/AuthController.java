package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.dto.auth.LoginRequest;
import com.projectweb.marktplace.dto.auth.LoginResponse;
import com.projectweb.marktplace.dto.auth.RefreshTokenRequest;
import com.projectweb.marktplace.dto.error.ErrorResponse;
import com.projectweb.marktplace.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e gerenciamento de tokens JWT")
public class AuthController {

    @Autowired
    private AuthService service;

    @Operation(summary = "Realizar login", description = "Autentica um usuário com email e senha, retornando tokens JWT de acesso e refresh")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "429", description = "Muitas tentativas de login. Tente novamente mais tarde", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        var response = service.authenticate(request.email(), request.password(), httpRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Renovar token de acesso", description = "Gera um novo token de acesso usando um refresh token válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token renovado com sucesso", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido ou expirado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request,
            HttpServletRequest httpRequest) {
        var response = service.refreshAccessToken(request.refreshToken(), httpRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Realizar logout", description = "Invalida o refresh token do usuário, encerrando a sessão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Logout realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        service.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

}
