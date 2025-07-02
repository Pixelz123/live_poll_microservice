package com.example.leaderboard_service.services;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import redis.clients.jedis.Jedis;

@Service
public class RedisSubscriberService {
    public  static final String REDIS_SERVER_IP = "127.0.0.1";
    public  static final int REDIS_SERVER_PORT = 6379;
    public  static final String LEADERBOARD_TIMESTAMP_CHANNEL = "leaderboard_timesup";
    public static final String REDIS_POLL_END_CHANNEL="poll_end_channel";
    public final RedisSubscriber redis_subscriber;
    public  final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    public RedisSubscriberService(RedisSubscriber redis_subscriber) {
        this.redis_subscriber = redis_subscriber;
    }

    @PostConstruct
    public void redis_subscription() {
        new Thread(() -> {
            jedis.subscribe(redis_subscriber, LEADERBOARD_TIMESTAMP_CHANNEL,REDIS_POLL_END_CHANNEL);
        }).start();
    }

}
