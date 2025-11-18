package com.projectweb.marktplace.controller.mapper;

import com.projectweb.marktplace.dto.auth.RegisterRequest;
import com.projectweb.marktplace.dto.auth.RegisterResponse;
import com.projectweb.marktplace.dto.user.UserResponse;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;


    public User toEntity(RegisterRequest dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(UserRole.USER);
        user.setActive(true);

        return user;
    }

    public RegisterResponse toRegisterResponse(User user) {
        return new RegisterResponse(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getActive(),
                user.getRole()
        );
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user);
    }
}
