package com.projectweb.marktplace.dto.user;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {
}
