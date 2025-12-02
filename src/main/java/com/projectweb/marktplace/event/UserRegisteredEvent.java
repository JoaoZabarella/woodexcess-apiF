package com.projectweb.marktplace.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent implements Serializable {
    private UUID userId;
    private String name;
    private String email;
    private LocalDateTime timestamp;
}
