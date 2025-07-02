package com.live_poll.poll_service.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    public static final String QUESTION_QUEUE = "questions.queue";
    public static final String QUESTION_ROUTING_KEY = "question.routing_key";
    public static final String EXCHANGE = "user.response.exchange";


}
