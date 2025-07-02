package com.live_poll.user_communication_service.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.live_poll.user_communication_service.configs.RabbitConfig;
@Component
public class EventPublisher {
       private final AmqpTemplate amqpTemplate;

    public EventPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
    public void sendForEvaluation(String message){
        System.out.println("senting to leaderboard service ::stage 2\n");
         amqpTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.USER_RESPONSE_ROUTING_KEY,
                message
        );
    }

}
