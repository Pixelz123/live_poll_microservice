package com.live_poll.poll_service.services;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisPublishServices {
    private final String REDIS_LEADERBOARD_CHANNEL="leaderboard_timesup";// use rabbitMQ here 
    private final String REDIS_POLL_END_CHANNEL="poll_end_channel";// this broad cast to usr com and leadeboard
    private static final String REDIS_SERVER_IP = "127.0.0.1";
    private static final int REDIS_SERVER_PORT = 6379;
  
    private final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);
  
    public void publish_timesup(String pollId){
        jedis.publish(REDIS_LEADERBOARD_CHANNEL,pollId);
    }
    public void publish_pollEnd(String pollId){
        jedis.publish(REDIS_POLL_END_CHANNEL,pollId);
    }

}