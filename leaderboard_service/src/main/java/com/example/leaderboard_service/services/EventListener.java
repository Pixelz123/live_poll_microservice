package com.example.leaderboard_service.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.leaderboard_service.DTO.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventListener {
    private final ObjectMapper object_mapper;
    private final RedisStateServices redis_service;
    private final AnalyticServices analytic_service;

    @Autowired
    public EventListener(ObjectMapper object_mapper, RedisStateServices redis_service,
            AnalyticServices analytic_service) {
        this.object_mapper = object_mapper;
        this.redis_service = redis_service;
        this.analytic_service = analytic_service;
    }

    @RabbitListener(queues = "user.response", ackMode = "AUTO")
    public void handleUserResponse(String responseJSON) {
       try {
            System.out.println("\n********************ingesting from queue**********************\n");
            UserResponseDTO response = object_mapper.readValue(responseJSON, UserResponseDTO.class);
            boolean isCorrect = analytic_service.evaluate_answer(
                    response.getPoll_id(),
                    response.getUser_id(),
                    response.getResponse(),
                    response.getIndex(), 1);

            redis_service.updateAnalytics(
                    response.getPoll_id(),
                    response.getIndex(),
                    String.valueOf(response.getResponse()));
            System.out.println("evaluated!!\n");
            if (isCorrect)
                redis_service.updateLeaderboard(response.getPoll_id(), response.getUser_id(), response.getPoints());
            else
                redis_service.updateLeaderboard(response.getPoll_id(), response.getUser_id(), 0);
           
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}