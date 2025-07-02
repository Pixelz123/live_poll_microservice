package com.live_poll.user_communication_service.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live_poll.user_communication_service.controllers.WebsocketController;
import com.live_poll.user_communication_service.dto.PollQuestionDTO;
import com.live_poll.user_communication_service.dto.PollQuestionEntity;

@Component
public class EventListener {
    public final ObjectMapper object_mapper;
    public final WebsocketController websocket_service;

    public EventListener(ObjectMapper object_mapper, WebsocketController websocket_service) {
        this.websocket_service = websocket_service;
        this.object_mapper = object_mapper;
    }

    @RabbitListener(queues = "questions.queue", ackMode = "AUTO")
    public void receive(String message) throws Exception {
        PollQuestionDTO question_message = object_mapper.readValue(message, PollQuestionDTO.class);
        String poll_id = question_message.getPollId();
        PollQuestionEntity question_data = question_message.getQuestion();
        websocket_service.sendQuestion(question_data, poll_id);
    }
}
