package com.live_poll.poll_service.dto;


import com.live_poll.poll_service.entities.PollQuestionEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PollQuestionDTO {
    private String pollId;
    private PollQuestionEntity question;
}
