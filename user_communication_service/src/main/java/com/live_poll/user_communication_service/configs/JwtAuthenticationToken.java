package com.live_poll.user_communication_service.configs;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private  UserDetails principal;

    // before authentication..
    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }
   

    @Override
    public UserDetails getPrincipal() {
        return principal;
    }

    @Override
    public String getCredentials() {
        return token;
    }
}
