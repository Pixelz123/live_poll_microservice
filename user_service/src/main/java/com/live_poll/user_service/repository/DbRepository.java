package com.live_poll.user_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.live_poll.user_service.dto.PollDataDTO;
import com.live_poll.user_service.enitities.PollEntity;

public interface DbRepository extends MongoRepository<PollEntity, String> {
    @Aggregation(pipeline = {
        "{ $match: { 'username': ?0 } }",
        "{ $project: { '_id': 1, 'poll_name': 1 } }"
    })
    public List<PollDataDTO> findPollIdsByUsername(String username);
}