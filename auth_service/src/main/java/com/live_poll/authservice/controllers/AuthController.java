package com.live_poll.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.live_poll.authservice.dto.AuthResponceDTO;
import com.live_poll.authservice.dto.UserDTO;
import com.live_poll.authservice.services.JwtServices;
import com.live_poll.authservice.services.UserServices;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserServices user_services;
    @Autowired
    private JwtServices jwt_services;

    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@RequestBody UserDTO userRequest) {
        user_services.registerUser(userRequest.username, userRequest.password);
        String jwt_token=jwt_services.createToken(userRequest.username);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponceDTO(userRequest.username,jwt_token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody UserDTO userRequest) {
        boolean isValid=user_services.validateUser(userRequest.username, userRequest.password);
        if (isValid){
            String jwt_token=jwt_services.createToken(userRequest.username);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AuthResponceDTO(userRequest.username,jwt_token));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials\n");
        }
    }
}
