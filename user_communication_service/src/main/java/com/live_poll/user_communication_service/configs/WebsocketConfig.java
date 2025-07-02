package com.live_poll.user_communication_service.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.live_poll.user_communication_service.services.JwtServices;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
  private final JwtServices jwtServices;

  @Autowired
  public WebsocketConfig(JwtServices jwtServices) {
    this.jwtServices = jwtServices;
  }

  @Bean
  public TaskScheduler websocketTaskScheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setThreadNamePrefix("websocket-heartbeat-");
    scheduler.setPoolSize(1);
    scheduler.initialize();
    return scheduler;
  }

  @Override
  public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {

    config.enableSimpleBroker("/topic", "/admin")
    .setHeartbeatValue(new long[] { 4000, 4000 })
    .setTaskScheduler(websocketTaskScheduler());
    config.setApplicationDestinationPrefixes("/poll");
  }

  @Override
  public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
  }

  @Override
  public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
    registration.interceptors(new StompChannelInterceptor(jwtServices)); // Register the interceptor
  }
}
