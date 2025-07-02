package com.live_poll.authservice.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.live_poll.authservice.enitites.UserEntity;

public interface DbRepository extends MongoRepository<UserEntity, String> {
    public Optional<UserEntity> findByUsername(String username);
}
