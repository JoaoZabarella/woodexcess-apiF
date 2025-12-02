package com.projectweb.marktplace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 100)
    private String eventType;

    @Column(length = 50)
    private String entityType;

    @Column
    private UUID entityId;

    @Column
    private UUID userId;

    @Column(length = 100)
    private String action;

    @Column(columnDefinition = "TEXT")
    private String details; // JSON

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
