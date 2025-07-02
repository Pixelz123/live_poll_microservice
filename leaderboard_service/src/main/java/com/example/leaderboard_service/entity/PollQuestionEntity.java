package com.example.leaderboard_service.entity;

import lombok.Data;

@Data
public class PollQuestionEntity {
    protected String question_id;
    protected String question_content;
    protected String [] options;
    protected int timeInSeconds;
    protected int correct_option; 
}
