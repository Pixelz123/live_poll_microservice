package com.live_poll.user_communication_service.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUESTION_QUEUE = "questions.queue";
    public static final String QUESTION_ROUTING_KEY = "question.routing_key";

    public static final String USER_RESPONSE_QUEUE = "user.response";
    public static final String EXCHANGE = "user.response.exchange";
    public static final String USER_RESPONSE_ROUTING_KEY="user.response.routing_key";



    @Bean
    public Queue QuestionQueue() {
        return new Queue(QUESTION_QUEUE, false);
    }


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding questionbinding(Queue questionQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(questionQueue)
                .to(exchange)
                .with(QUESTION_ROUTING_KEY);
    }
}
