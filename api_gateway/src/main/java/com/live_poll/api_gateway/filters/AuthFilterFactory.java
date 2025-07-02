package com.live_poll.api_gateway.filters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.live_poll.api_gateway.services.JwtServices;

@Component
public class AuthFilterFactory implements GatewayFilterFactory<AuthFilterFactory.Config>{
    @Autowired 
    private  JwtServices jwt_services;
    @Override 
    public GatewayFilter apply(Config config){
        return new JwtAuthenticationFilter(jwt_services);
    }
    @Override
    public String name(){
        return "AuthFilterFactory";
    }
    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }
    @Override
    public Config newConfig() {
        return new Config(); 
    }
    public static class Config{}
}
