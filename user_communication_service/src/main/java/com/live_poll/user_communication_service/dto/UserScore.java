package com.live_poll.user_communication_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserScore {
    private String user_name;
    private double score;
}
