package com.live_poll.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponceDTO {
    public String username;
    public String token;
}
