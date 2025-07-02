package com.example.leaderboard_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPubSub;

@Service
public class RedisSubscriber extends JedisPubSub {
  private final RedisPublishServices redis_publish_service;
  private final RedisStateServices redis_state_service;
  @Autowired
  public RedisSubscriber(RedisPublishServices redis_publish_service,RedisStateServices redis_state_service) {
    this.redis_publish_service = redis_publish_service;
    this.redis_state_service=redis_state_service;
  }

  @Override
  public void onMessage(String channel, String pollId) {
    try {
      if (channel.equals(RedisSubscriberService.LEADERBOARD_TIMESTAMP_CHANNEL)) {
        System.out.println("times up .. sending poll::" + pollId + "\n");
        redis_publish_service.publish_leaderboard(pollId);
      }
      else if(channel.equals(RedisSubscriberService.REDIS_POLL_END_CHANNEL)){
        redis_state_service.deleteLeaderboard(pollId);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
