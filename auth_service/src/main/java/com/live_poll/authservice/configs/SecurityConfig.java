package com.live_poll.authservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
//     @Bean
//     public AuthenticationManager authenticationManager() throws Exception{
//         // UsernamePasswordAuthenticationToken
//         return new ProviderManager(Collections.emptyList());
//     }
    @Bean 
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
       return http
              .csrf(ServerHttpSecurity.CsrfSpec::disable)
              .cors(ServerHttpSecurity.CorsSpec::disable)
              .authorizeExchange(exchanges -> exchanges
                                             .pathMatchers("/auth/**").permitAll()
                                             .anyExchange().authenticated()) 
              .httpBasic(httpBasic->httpBasic.disable())
              .formLogin(formBasic->formBasic.disable())
              .build();
    }
    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
