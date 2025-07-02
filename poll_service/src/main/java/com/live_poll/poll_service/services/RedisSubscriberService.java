package com.live_poll.poll_service.services;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import redis.clients.jedis.Jedis;

@Service
public class RedisSubscriberService {
    public  static final String REDIS_SERVER_IP = "127.0.0.1";
    public  static final int REDIS_SERVER_PORT = 6379;
    public  static final String REDIS_START_CHANNEL = "start_channel";
    public  static final String REDIS_PROCEED_CHANNEL = "proceed_channel";
    public final RedisSubscriber redis_subscriber;
    private final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    public RedisSubscriberService(RedisSubscriber redis_subscriber) {
        this.redis_subscriber=redis_subscriber;
    }

    @PostConstruct
    public void redis_subscription() {
        new Thread(() -> {
            jedis.subscribe(redis_subscriber, REDIS_START_CHANNEL,REDIS_PROCEED_CHANNEL);
        }).start();
    }
}