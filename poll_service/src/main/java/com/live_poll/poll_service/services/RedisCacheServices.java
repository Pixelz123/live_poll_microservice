package com.live_poll.poll_service.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live_poll.poll_service.entities.PollEntity;
import com.live_poll.poll_service.entities.PollQuestionEntity;

import redis.clients.jedis.Jedis;

@Service
public class RedisCacheServices {

    private final Dbservices db_services;
    private final ObjectMapper objectMapper;

    private static final String REDIS_SERVER_IP = "127.0.0.1";
    private static final int REDIS_SERVER_PORT = 6379;
    private static final String POLL_STATE_KEY_PREFIX = "poll:state:";
    private static final String POLL_TIMER_ZSET_KEY = "poll:timerQueue";
    private static final String POLL_QUESTION_SET_PREFIX = "poll:questionSet:";

    private final Jedis jedis = new Jedis(REDIS_SERVER_IP, REDIS_SERVER_PORT);

    @Autowired
    public RedisCacheServices(Dbservices db_services, ObjectMapper objectMapper) {
        this.db_services = db_services;
        this.objectMapper = objectMapper;
    }

    public List<String> getActivePolls(long timestamp) {
        return jedis.zrangeByScore(POLL_TIMER_ZSET_KEY, 0, timestamp);
    }

    public void updatePollSchedule(String poll_id, long timestamp) {
        jedis.zadd(POLL_TIMER_ZSET_KEY, timestamp, poll_id);
    }

    public void removePollFromSchedule(String poll_id) {
        jedis.zrem(POLL_TIMER_ZSET_KEY, poll_id);
    }

    public void savePollState(String pollId, String json) {
        jedis.set(POLL_STATE_KEY_PREFIX + pollId, json);
    }

    public String getPollState(String pollId) {
        return jedis.get(POLL_STATE_KEY_PREFIX + pollId);
    }

    public void deletePollState(String pollId) {
        jedis.del(POLL_STATE_KEY_PREFIX + pollId);
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
    public int fetchTotalQuestion(String pollId) throws Exception {
        String json = jedis.get(POLL_QUESTION_SET_PREFIX + pollId);
        if (!jedis.exists(POLL_QUESTION_SET_PREFIX + pollId)) {
            System.out.println("\n******Cache miss******\n");
            json = cacheQuestionSet(pollId);
        }
        PollEntity poll = objectMapper.readValue(json, PollEntity.class);
        return poll.getQuestion_set().size();
    }

    public String cacheQuestionSet(String pollId) throws Exception {
        System.out.println("\n*********tapping the db layer cache miss..******\n");
        PollEntity poll = db_services.getPoll(pollId);
        String json = objectMapper.writeValueAsString(poll);
        jedis.set(POLL_QUESTION_SET_PREFIX + pollId, json);
        return json;
    }
}