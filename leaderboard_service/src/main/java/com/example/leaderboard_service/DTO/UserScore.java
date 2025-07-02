package com.example.leaderboard_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserScore {
    private String user_name;
    private double score;
}
