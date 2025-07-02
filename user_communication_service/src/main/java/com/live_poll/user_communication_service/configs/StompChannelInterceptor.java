package com.live_poll.user_communication_service.configs;

import java.security.Principal;

import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import com.live_poll.user_communication_service.services.JwtServices;

public class StompChannelInterceptor implements ChannelInterceptor {

    private final JwtServices jwtServices;

    public StompChannelInterceptor(JwtServices jwtServices) {
        this.jwtServices = jwtServices;
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message,@NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); 
                if (jwtServices.validateToken(token)) {
                    Principal user = new JwtAuthenticationToken(jwtServices.extractUsername(token)); // Extract user info
                    accessor.setUser(user); 
                    return message; 
                }
            }

            throw new IllegalArgumentException("Invalid token");
        }

        return message; 
    }
}