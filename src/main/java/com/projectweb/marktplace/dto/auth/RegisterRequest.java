package com.projectweb.marktplace.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
        @NotBlank(message = "Phone is required") @Size(max = 20, message = "A phone can only have 20 numbers.") String phone,
        @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String password

) {}
