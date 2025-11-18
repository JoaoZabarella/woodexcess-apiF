package com.projectweb.marktplace.dto.auth;

import com.projectweb.marktplace.role.UserRole;

public record RegisterResponse(
        String id,
        String name,
        String email,
        String phone,
        Boolean active,
        UserRole role
) {
}

