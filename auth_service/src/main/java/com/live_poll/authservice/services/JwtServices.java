package com.live_poll.authservice.services;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServices {
    private final String SECRET_KEY = "jwt_hyper_secret_key_must_not_be_shared##@#";
    private final long EXPIRATION_TIME_MILI=360000000;
    
    public String createToken(String username)
    {
       return Jwts
              .builder()
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date (System.currentTimeMillis()+EXPIRATION_TIME_MILI))
              .signWith(getSecurityKey())
              .compact();
    }
    private Key getSecurityKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
