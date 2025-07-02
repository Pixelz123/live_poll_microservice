// package com.live_poll.authservice.configs;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.core.Authentication;

// import com.live_poll.authservice.enitites.UserEntity;
// import com.live_poll.authservice.services.JwtServices;
// import com.live_poll.authservice.services.UserServices;

// public class JwtAuthenticationProvider implements AuthenticationProvider {
//     @Autowired
//     private JwtServices jwt_services;
//     @Autowired
//     private UserServices user_services;

//     @Override
//     public Authentication authenticate(Authentication authentication) {
//         String token = (String) authentication.getCredentials();
//         String username = jwt_services.extractUsername(token);
//         if (username == null) {
//             throw new BadCredentialsException("User does not exist..\n");
//         }
//         UserEntity user = user_services.loadUserByUsername(username);
//         if (!jwt_services.validateToken(token)) {
//             throw new BadCredentialsException("Invalid JWT token");
//         }
//         return new JwtAuthenticationToken(user,user.getAuthorities());
//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return JwtAuthenticationToken.class.isAssignableFrom(authentication);
//     }
// }
