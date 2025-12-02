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
public class MessageReceivedEvent implements Serializable {
    private UUID messageId;
    private UUID senderId;
    private String senderName;
    private UUID receiverId;
    private String receiverName;
    private UUID adId;
    private String adTitle;
    private String content;
    private LocalDateTime timestamp;
}
