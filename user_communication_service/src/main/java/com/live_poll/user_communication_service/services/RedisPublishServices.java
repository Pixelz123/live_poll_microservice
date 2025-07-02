package com.live_poll.user_communication_service.services;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisPublishServices {
    private final String REDIS_START_CHANNEL="start_channel";
    public  static final String REDIS_PROCEED_CHANNEL = "proceed_channel";
    private static final String REDIS_SERVER_IP = "127.0.0.1";
    private static final int REDIS_SERVER_PORT = 6379;

    private final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    public void publish_start(String pollId){
        jedis.publish(REDIS_START_CHANNEL,pollId);
    }
    public void publish_proceed(String pollId){
        jedis.publish(REDIS_PROCEED_CHANNEL,pollId);
    }

}
