package com.projectweb.marktplace.dto.user;

import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private Boolean active;
    private UserRole role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.active = user.getActive();
        this.role = user.getRole();

    }
}
