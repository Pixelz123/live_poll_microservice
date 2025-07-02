package com.example.leaderboard_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaderboard_service.entity.PollEntity;
import com.example.leaderboard_service.repositories.DbRepository;


@Service
public class Dbservices {
   @Autowired
   public DbRepository db_repo;

   public PollEntity getPoll(String poll_id) {
      Optional<PollEntity> poll = db_repo.findById(poll_id);
      if (poll.isPresent()) {
         return poll.get();
      } else {
         return null;
      }
   }
}