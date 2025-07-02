package com.live_poll.poll_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.live_poll.poll_service.entities.PollEntity;

public interface  DbRepository extends MongoRepository<PollEntity, String>{}
