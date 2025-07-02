package com.live_poll.user_service.enitities;
import lombok.Data;

@Data
public class PollQuestionEntity {
    public  String question_id;
    public  String question_content;
    public  String [] options;
    public  int timeInSeconds;
    public  int correct_option;
    public  int points;
}
