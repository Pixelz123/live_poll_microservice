package com.live_poll.user_communication_service.services;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live_poll.user_communication_service.controllers.WebsocketController;
import com.live_poll.user_communication_service.dto.LeaderboardDTO;

import redis.clients.jedis.JedisPubSub;

@Service
public class RedisSubscriber extends JedisPubSub {
  private final ObjectMapper object_mapper;
  private final WebsocketController websocket_controller;

  public RedisSubscriber(ObjectMapper object_mapper, WebsocketController websocket_controller) {
    this.object_mapper = object_mapper;
    this.websocket_controller = websocket_controller;
  }

  @Override
  public void onMessage(String channel, String message) {
    try {
      if (channel.equals(RedisSubscriberService.LEADERBOARD_CHANNEL)) {
        LeaderboardDTO leaderboard = object_mapper.readValue(message, LeaderboardDTO.class);
        websocket_controller.sendLeaderboard(leaderboard);
      }
      else if (channel.equals(RedisSubscriberService.REDIS_POLL_END_CHANNEL)){
        websocket_controller.sendEndMessage(message);
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

}
