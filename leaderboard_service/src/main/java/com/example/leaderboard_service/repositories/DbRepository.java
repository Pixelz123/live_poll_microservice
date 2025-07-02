package com.example.leaderboard_service.repositories;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.leaderboard_service.entity.PollEntity;



public interface  DbRepository extends MongoRepository<PollEntity, String>{
}
