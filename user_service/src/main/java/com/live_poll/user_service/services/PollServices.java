package com.live_poll.user_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.live_poll.user_service.dto.PollDataDTO;
import com.live_poll.user_service.enitities.PollEntity;
import com.live_poll.user_service.repository.DbRepository;

@Service
public class PollServices {
    @Autowired 
    DbRepository db_repo;

    public void saveNewPoll(PollEntity poll) {
        db_repo.save(poll);
    }

    public List<PollDataDTO> getUserPolls(String username) {
        return db_repo.findPollIdsByUsername(username);
    }
    
}
