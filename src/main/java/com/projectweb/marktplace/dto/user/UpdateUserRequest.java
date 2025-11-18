package com.projectweb.marktplace.dto.user;

public record UpdateUserRequest(
        String name,
        String email
) {
}
