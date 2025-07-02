package com.live_poll.poll_service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPubSub;

@Service
public class RedisSubscriber extends JedisPubSub{
    @Autowired
    private PollManagerServices poll_services;
    @Override
    public void onMessage (String channel , String pollId) {
        try
         {
            if (channel.equals(RedisSubscriberService.REDIS_START_CHANNEL))
             poll_services.startPoll(pollId);
            else if (channel.equals(RedisSubscriberService.REDIS_PROCEED_CHANNEL))
             poll_services.advanceToNextQuestion(pollId);
        } 
        catch(Exception e){
             System.out.println("Redis subscription to start channel fails ....\n");
        }
    }
    
}