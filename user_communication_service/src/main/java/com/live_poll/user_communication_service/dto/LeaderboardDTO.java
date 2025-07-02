package com.live_poll.user_communication_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class LeaderboardDTO {
   public String pollId;
   public List<UserScore> scoreboard;
}

