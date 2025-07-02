package com.example.leaderboard_service.DTO;

import java.util.List;

import lombok.Data;

@Data
public class LeaderboardDTO {
   public String pollId;
   public List<UserScore> scoreboard;    
}
