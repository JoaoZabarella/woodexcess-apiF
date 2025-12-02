package com.projectweb.marktplace.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange
    public static final String EXCHANGE_NAME = "marketplace.events";

    // Queues
    public static final String NOTIFICATIONS_QUEUE = "marketplace.notifications";
    public static final String EMAILS_QUEUE = "marketplace.emails";
    public static final String AUDIT_QUEUE = "marketplace.audit";

    // Routing Keys
    public static final String USER_REGISTERED_KEY = "user.registered";
    public static final String PURCHASE_CREATED_KEY = "purchase.created";
    public static final String MESSAGE_RECEIVED_KEY = "message.received";
    public static final String RATING_CREATED_KEY = "rating.created";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue notificationsQueue() {
        return QueueBuilder.durable(NOTIFICATIONS_QUEUE)
                .withArgument("x-message-ttl", 604800000) // 7 days
                .build();
    }

    @Bean
    public Queue emailsQueue() {
        return QueueBuilder.durable(EMAILS_QUEUE)
                .withArgument("x-message-ttl", 604800000) // 7 days
                .build();
    }

    @Bean
    public Queue auditQueue() {
        return QueueBuilder.durable(AUDIT_QUEUE)
                .withArgument("x-message-ttl", 2592000000L) // 30 days
                .build();
    }

    // Bindings for Notifications Queue
    @Bean
    public Binding purchaseCreatedToNotifications(Queue notificationsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationsQueue).to(exchange).with(PURCHASE_CREATED_KEY);
    }

    @Bean
    public Binding messageReceivedToNotifications(Queue notificationsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationsQueue).to(exchange).with(MESSAGE_RECEIVED_KEY);
    }

    @Bean
    public Binding ratingCreatedToNotifications(Queue notificationsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationsQueue).to(exchange).with(RATING_CREATED_KEY);
    }

    // Bindings for Emails Queue
    @Bean
    public Binding userRegisteredToEmails(Queue emailsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailsQueue).to(exchange).with(USER_REGISTERED_KEY);
    }

    @Bean
    public Binding purchaseCreatedToEmails(Queue emailsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailsQueue).to(exchange).with(PURCHASE_CREATED_KEY);
    }

    // Binding for Audit Queue (all events)
    @Bean
    public Binding allEventsToAudit(Queue auditQueue, TopicExchange exchange) {
        return BindingBuilder.bind(auditQueue).to(exchange).with("#");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
