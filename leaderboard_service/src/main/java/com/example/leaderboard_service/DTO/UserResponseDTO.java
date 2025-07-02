package com.example.leaderboard_service.DTO;
import lombok.Data;


@Data
public class UserResponseDTO {
    private String user_id;
    private String poll_id;
    private int index;
    private int  response;
    private int points;
}