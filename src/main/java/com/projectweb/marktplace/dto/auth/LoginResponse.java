package com.projectweb.marktplace.dto.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn

){
    public LoginResponse(String accessToken, String refreshToken, Long expiresIn){
        this(accessToken, refreshToken, "Bearer", expiresIn);
    }
}
