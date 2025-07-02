package com.example.leaderboard_service.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaderboard_service.DTO.LeaderboardDTO;
import com.example.leaderboard_service.DTO.UserScore;
import com.example.leaderboard_service.entity.PollEntity;
import com.example.leaderboard_service.entity.PollQuestionEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZAddParams;
import redis.clients.jedis.resps.Tuple;

@Service
public class RedisStateServices {
    // maintain a ey value set for leaderboard
    private static final String REDIS_SERVER_IP = "127.0.0.1";
    private static final int REDIS_SERVER_PORT = 6379;
    private static final String LEADERBOARD_ZSET = "poll.scoreboard:";
    private static final String POLL_QUESTION_SET_PREFIX = "poll:questionSet:";
    private static final String POLL_QUESTION_ANALYTICS = "poll:questionAnalytics:";

    private final Dbservices db_services;
    private final ObjectMapper objectMapper;
    private final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    @Autowired
    public RedisStateServices(Dbservices db_services, ObjectMapper objectMapper) {
        this.db_services = db_services;
        this.objectMapper = objectMapper;
    }

    public void updateAnalytics(String pollId, int index, String response) {
        jedis.zaddIncr(POLL_QUESTION_ANALYTICS + pollId + index, 1.0, response, new ZAddParams());
    }

    public void updateLeaderboard(String pollId, String userId, double points) {
        jedis.zaddIncr(LEADERBOARD_ZSET + pollId, points, userId, new ZAddParams());
    }

    public LeaderboardDTO  getLeaderboard(String pollId){
         List<Tuple> stat=jedis.zrevrangeWithScores(LEADERBOARD_ZSET + pollId, 0,-1);
         LeaderboardDTO leaderboard=new LeaderboardDTO();
         leaderboard.setPollId(pollId);
         leaderboard.setScoreboard(new ArrayList<>());
         for (Tuple t: stat){
            leaderboard.scoreboard.add(new UserScore(t.getElement(),t.getScore()));
         }
         return leaderboard;
    }

    public void deleteLeaderboard(String pollId){
        String zset=LEADERBOARD_ZSET+pollId;
        jedis.del(zset);
    }
   
    public List<Tuple> getQuestionAnalytics(String pollId,String index){
        return jedis.zrangeWithScores(POLL_QUESTION_ANALYTICS+pollId+index,0,-1);
    }

    public PollQuestionEntity fetchQuestion(String pollId, int index) throws Exception {
        String json = jedis.get(POLL_QUESTION_SET_PREFIX + pollId);
        if (!jedis.exists(POLL_QUESTION_SET_PREFIX + pollId)) {
            System.out.println("\n******Cache miss******\n");
            json = cacheQuestionSet(pollId);
        }
        PollEntity poll = objectMapper.readValue(json, PollEntity.class);
        return poll.getQuestion_set().get(index);

    }

    public String cacheQuestionSet(String pollId) throws Exception {
        System.out.println("\ntapping the db layer cache miss..******\n");
        PollEntity poll = db_services.getPoll(pollId);
        String json = objectMapper.writeValueAsString(poll);
        jedis.set(POLL_QUESTION_SET_PREFIX + pollId, json);
        return json;
    }
}
