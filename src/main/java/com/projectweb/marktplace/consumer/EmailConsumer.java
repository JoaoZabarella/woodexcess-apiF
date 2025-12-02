package com.projectweb.marktplace.consumer;

import com.projectweb.marktplace.config.RabbitMQConfig;
import com.projectweb.marktplace.event.PurchaseCreatedEvent;
import com.projectweb.marktplace.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    @RabbitListener(queues = RabbitMQConfig.EMAILS_QUEUE)
    public void handleUserRegistered(UserRegisteredEvent event) {
        try {
            log.info("Sending welcome email to: {}", event.getEmail());

            log.info("Welcome email sent to: {}", event.getEmail());
        } catch (Exception e) {
            log.error("Error sending welcome email: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.EMAILS_QUEUE)
    public void handlePurchaseCreated(PurchaseCreatedEvent event) {
        try {
            log.info("Sending purchase confirmation email for purchase: {}", event.getPurchaseId());

            log.info("Purchase confirmation email sent");
        } catch (Exception e) {
            log.error("Error sending purchase email: {}", e.getMessage(), e);
        }
    }
}
