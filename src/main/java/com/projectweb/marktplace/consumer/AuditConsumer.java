package com.projectweb.marktplace.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectweb.marktplace.config.RabbitMQConfig;
import com.projectweb.marktplace.event.*;
import com.projectweb.marktplace.model.AuditLog;
import com.projectweb.marktplace.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class AuditConsumer {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditConsumer(AuditLogRepository auditLogRepository, ObjectMapper objectMapper) {
        this.auditLogRepository = auditLogRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handleUserRegistered(UserRegisteredEvent event) {
        try {
            log.info("Audit: UserRegisteredEvent received for user: {}", event.getUserId());

            AuditLog auditLog = new AuditLog();
            auditLog.setEventType("UserRegisteredEvent");
            auditLog.setEntityType("User");
            auditLog.setEntityId(event.getUserId());
            auditLog.setUserId(event.getUserId());
            auditLog.setAction("REGISTERED");
            auditLog.setDetails(objectMapper.writeValueAsString(event));
            auditLog.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(auditLog);
            log.info("Audit log created for user registration: {}", event.getUserId());
        } catch (Exception e) {
            log.error("Error processing UserRegisteredEvent in audit: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handlePurchaseCreated(PurchaseCreatedEvent event) {
        try {
            log.info("Audit: PurchaseCreatedEvent received for purchase: {}", event.getPurchaseId());

            AuditLog auditLog = new AuditLog();
            auditLog.setEventType("PurchaseCreatedEvent");
            auditLog.setEntityType("Purchase");
            auditLog.setEntityId(event.getPurchaseId());
            auditLog.setUserId(event.getBuyerId());
            auditLog.setAction("CREATED");
            auditLog.setDetails(objectMapper.writeValueAsString(event));
            auditLog.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(auditLog);
            log.info("Audit log created for purchase: {}", event.getPurchaseId());
        } catch (Exception e) {
            log.error("Error processing PurchaseCreatedEvent in audit: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handleMessageReceived(MessageReceivedEvent event) {
        try {
            log.info("Audit: MessageReceivedEvent received for message: {}", event.getMessageId());

            AuditLog auditLog = new AuditLog();
            auditLog.setEventType("MessageReceivedEvent");
            auditLog.setEntityType("Message");
            auditLog.setEntityId(event.getMessageId());
            auditLog.setUserId(event.getSenderId());
            auditLog.setAction("SENT");
            auditLog.setDetails(objectMapper.writeValueAsString(event));
            auditLog.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(auditLog);
            log.info("Audit log created for message: {}", event.getMessageId());
        } catch (Exception e) {
            log.error("Error processing MessageReceivedEvent in audit: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handleRatingCreated(RatingCreatedEvent event) {
        try {
            log.info("Audit: RatingCreatedEvent received for rating: {}", event.getRatingId());

            AuditLog auditLog = new AuditLog();
            auditLog.setEventType("RatingCreatedEvent");
            auditLog.setEntityType("Rating");
            auditLog.setEntityId(event.getRatingId());
            auditLog.setUserId(event.getUserId());
            auditLog.setAction("CREATED");
            auditLog.setDetails(objectMapper.writeValueAsString(event));
            auditLog.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(auditLog);
            log.info("Audit log created for rating: {}", event.getRatingId());
        } catch (Exception e) {
            log.error("Error processing RatingCreatedEvent in audit: {}", e.getMessage(), e);
        }
    }
}
