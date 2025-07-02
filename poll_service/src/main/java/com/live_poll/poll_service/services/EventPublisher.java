package com.live_poll.poll_service.services;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.live_poll.poll_service.config.RabbitConfig;


@Service
public class EventPublisher {

    private final AmqpTemplate amqpTemplate;
    public EventPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendQuestion(String message) {
        // to user_communication
        amqpTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.QUESTION_ROUTING_KEY,
                message
        );
    }
    public void sendAnalytics(String message){
        // to user_communication
        amqpTemplate.convertAndSend(
               RabbitConfig.EXCHANGE,
               RabbitConfig.QUESTION_ROUTING_KEY,
               message
        );
    }
}
