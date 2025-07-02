package com.live_poll.user_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.live_poll.user_service.dto.PollDataDTO;
import com.live_poll.user_service.enitities.PollEntity;
import com.live_poll.user_service.services.PollServices;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    PollServices poll_services;

    @PostMapping("/savePoll")
    public ResponseEntity<?> handleSave(@RequestBody PollEntity newPoll) {
        poll_services.saveNewPoll(newPoll);
        return ResponseEntity.status(HttpStatus.CREATED).body("Poll saved sucessfully!!\n");
    }
   

    @GetMapping("/getUserPolls/{username}")
    public ResponseEntity<?> handleGetPolls(@PathVariable("username") String username) {
        List<PollDataDTO> pollIds = poll_services.getUserPolls(username);
        return ResponseEntity.ok(pollIds);
    }
}
