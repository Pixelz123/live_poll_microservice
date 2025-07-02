package com.live_poll.user_communication_service.dto;

import lombok.Data;

@Data
public class PollQuestionDTO {
    private String pollId;
    private PollQuestionEntity question;
}
