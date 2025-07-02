package com.example.leaderboard_service.services;

import org.springframework.stereotype.Service;

import com.example.leaderboard_service.DTO.LeaderboardDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

@Service
public class RedisPublishServices {
    private final String LEADERBOARD_CHANNEL = "leaderboard_channel";
    private static final String REDIS_SERVER_IP = "127.0.0.1";
    private static final int REDIS_SERVER_PORT = 6379;
    private final RedisStateServices redis_state_service;
    private final ObjectMapper object_mapper;

    public RedisPublishServices(ObjectMapper object_mapper, RedisStateServices redis_state_service) {
        this.redis_state_service = redis_state_service;
        this.object_mapper = object_mapper;
    }

    private final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    public void publish_leaderboard (String pollId) throws JsonProcessingException
    {
        LeaderboardDTO leaderboard = redis_state_service.getLeaderboard(pollId);
        System.out.println("publishing leaderboard..\n");
        jedis.publish(LEADERBOARD_CHANNEL, object_mapper.writeValueAsString(leaderboard));
    }

}
