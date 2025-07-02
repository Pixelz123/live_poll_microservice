package com.live_poll.authservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.live_poll.authservice.enitites.UserEntity;
import com.live_poll.authservice.repositories.DbRepository;

@Service
public class UserServices implements UserDetailsService{
    @Autowired
    DbRepository db_repo;
    @Autowired 
    PasswordEncoder passwordEncoder;
    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException{
       Optional<UserEntity> user=db_repo.findByUsername(username);
       if(user.isPresent())
        return user.get();
       else
        return null; 
    }
    public void registerUser(String username ,String password){
       UserEntity user=new UserEntity();
       user.setUsername(username);
       user.setPassword(passwordEncoder.encode(password));
       db_repo.save(user);
    }
    public boolean validateUser(String username, String password) {
        UserEntity user = loadUserByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
    
}
