package com.projectweb.marktplace.event;

import com.projectweb.marktplace.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserRegistered(UserRegisteredEvent event) {
        log.info("Publishing UserRegisteredEvent for user: {}", event.getUserId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.USER_REGISTERED_KEY,
                event);
    }

    public void publishPurchaseCreated(PurchaseCreatedEvent event) {
        log.info("Publishing PurchaseCreatedEvent for purchase: {}", event.getPurchaseId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.PURCHASE_CREATED_KEY,
                event);
    }

    public void publishMessageReceived(MessageReceivedEvent event) {
        log.info("Publishing MessageReceivedEvent for message: {}", event.getMessageId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.MESSAGE_RECEIVED_KEY,
                event);
    }

    public void publishRatingCreated(RatingCreatedEvent event) {
        log.info("Publishing RatingCreatedEvent for rating: {}", event.getRatingId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.RATING_CREATED_KEY,
                event);
    }
}
