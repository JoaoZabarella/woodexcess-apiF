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
public class RatingCreatedEvent implements Serializable {
    private UUID ratingId;
    private UUID userId;
    private String userName;
    private UUID purchaseId;
    private Integer score;
    private LocalDateTime timestamp;
}
