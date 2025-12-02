package com.projectweb.marktplace.consumer;

import com.projectweb.marktplace.config.RabbitMQConfig;
import com.projectweb.marktplace.event.MessageReceivedEvent;
import com.projectweb.marktplace.event.PurchaseCreatedEvent;
import com.projectweb.marktplace.event.RatingCreatedEvent;
import com.projectweb.marktplace.model.Notification;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.NotificationRepository;
import com.projectweb.marktplace.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationConsumer(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATIONS_QUEUE)
    public void handlePurchaseCreated(PurchaseCreatedEvent event) {
        try {
            log.info("Creating notification for seller about new purchase: {}", event.getPurchaseId());

            User seller = userRepository.findById(event.getSellerId())
                    .orElseThrow(() -> new RuntimeException("Seller not found"));

            Notification notification = new Notification();
            notification.setUser(seller);
            notification.setType("PURCHASE");
            notification.setTitle("Nova Compra!");
            notification.setContent(String.format("%s comprou seu anúncio '%s'",
                    event.getBuyerName(), event.getAdTitle()));
            notification.setRead(false);
            notification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(notification);
            log.info("Notification created for seller: {}", seller.getId());
        } catch (Exception e) {
            log.error("Error creating purchase notification: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATIONS_QUEUE)
    public void handleMessageReceived(MessageReceivedEvent event) {
        try {
            log.info("Creating notification for message receiver: {}", event.getReceiverId());

            User receiver = userRepository.findById(event.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));

            Notification notification = new Notification();
            notification.setUser(receiver);
            notification.setType("MESSAGE");
            notification.setTitle("Nova Mensagem!");
            notification.setContent(String.format("%s enviou uma mensagem sobre '%s'",
                    event.getSenderName(), event.getAdTitle()));
            notification.setRead(false);
            notification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(notification);
            log.info("Notification created for receiver: {}", receiver.getId());
        } catch (Exception e) {
            log.error("Error creating message notification: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATIONS_QUEUE)
    public void handleRatingCreated(RatingCreatedEvent event) {
        try {
            log.info("Processing rating created event: {}", event.getRatingId());
            log.info("Rating event processed: {}", event.getRatingId());
        } catch (Exception e) {
            log.error("Error processing rating event: {}", e.getMessage(), e);
        }
    }
}
