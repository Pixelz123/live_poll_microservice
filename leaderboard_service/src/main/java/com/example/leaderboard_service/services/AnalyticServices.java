package com.example.leaderboard_service.services;

import org.springframework.stereotype.Service;

import com.example.leaderboard_service.entity.PollQuestionEntity;

@Service
public class AnalyticServices {
    private final RedisStateServices redis_service;

    public AnalyticServices(RedisStateServices redis_service) {

        this.redis_service = redis_service;
    }

    public boolean evaluate_answer(String pollId, String userId, int choice, int index, int points) throws Exception {
        boolean isCorrect = false;
        PollQuestionEntity question = redis_service.fetchQuestion(pollId, index);
        if (question.getCorrect_option() == choice)
            isCorrect = true;
        return isCorrect;
    }
    
}
