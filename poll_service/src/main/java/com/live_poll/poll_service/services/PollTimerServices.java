package com.live_poll.poll_service.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PollTimerServices {

    private final RedisCacheServices redis_service;
    private final PollManagerServices poll_service;

    @Autowired
    public PollTimerServices(RedisCacheServices redis_ser, PollManagerServices poll_ser) {
        redis_service = redis_ser;
        poll_service = poll_ser;
    }

    @Scheduled(fixedDelay = 1000)
    public void cheakAndAdvancePoll() throws Exception{
        long now = System.currentTimeMillis();
        List<String> activePolls = redis_service.getActivePolls(now);
        for (String pollId : activePolls) {
            // when times up for a question send the leadrboard not advance to the next question 
            // let the presenter do it 
            poll_service.sendwait(pollId);
        }
    }
}
