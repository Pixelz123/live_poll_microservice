package com.example.leaderboard_service.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String USER_RESPONSE_QUEUE = "user.response";
    public static final String USER_RESPONSE_ROUTING_KEY = "user.response.routing_key";
    public static final String EXCHANGE = "user.response.exchange";

    @Bean
    public Queue ResponseQueue() {
        return new Queue(USER_RESPONSE_QUEUE, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding responsebinding(Queue responseQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(responseQueue)
                .to(exchange)
                .with(USER_RESPONSE_ROUTING_KEY);
    }
}
