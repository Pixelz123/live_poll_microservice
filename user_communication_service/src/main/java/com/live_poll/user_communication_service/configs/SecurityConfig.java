package com.live_poll.user_communication_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
              .csrf(csrf->csrf.disable())
              .cors(cors->cors.disable())
              .formLogin(formLogin->formLogin.disable())
              .httpBasic(httpBasic->httpBasic.disable())
              .authorizeHttpRequests(auth->auth
                             .requestMatchers("/ws/**").permitAll()
                             .anyRequest().authenticated())
              .build();
    }    
}
