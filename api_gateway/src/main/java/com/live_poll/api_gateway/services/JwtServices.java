package com.live_poll.api_gateway.services;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServices {
    private final String SECRET_KEY = "jwt_hyper_secret_key_must_not_be_shared##@#";

    public boolean validateToken(String token) {
        String username = extractUsername(token);
        return (!isTokenExpired(token) && username != null && !username.isEmpty());
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecurityKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSecurityKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
