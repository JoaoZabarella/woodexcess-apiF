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
public class PurchaseCreatedEvent implements Serializable {
    private UUID purchaseId;
    private UUID buyerId;
    private String buyerName;
    private UUID adId;
    private String adTitle;
    private UUID sellerId;
    private String sellerName;
    private LocalDateTime timestamp;
}
