package com.live_poll.poll_service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live_poll.poll_service.dto.PollQuestionDTO;
import com.live_poll.poll_service.entities.PollCacheMetadata;
import com.live_poll.poll_service.entities.PollQuestionEntity;

@Service
public class PollManagerServices {
    private final RedisCacheServices redis_service;
    private final EventPublisher rabbitMQ_service;
    private final RedisPublishServices redis_pub_service;
    private final ObjectMapper objectMapper;

    @Autowired
    public PollManagerServices(RedisCacheServices redis_service, ObjectMapper objectMapper,
            EventPublisher rabbitMQ_service, RedisPublishServices redis_pub_service) {
        this.rabbitMQ_service = rabbitMQ_service;
        this.redis_pub_service = redis_pub_service;
        this.redis_service = redis_service;
        this.objectMapper = objectMapper;
    }

    public void startPoll(String pollId) throws Exception {
        long timestamp = System.currentTimeMillis();
        long firstQuestionTime = redis_service.fetchQuestion(pollId, 0).getTimeInSeconds() * 1000;
        timestamp += firstQuestionTime;

        redis_service.updatePollSchedule(pollId, timestamp);

        PollCacheMetadata cache = new PollCacheMetadata();
        cache.setCurrentIndex(0);
        cache.setEnded(false);
        cache.setTotal_questions(redis_service.fetchTotalQuestion(pollId));

        String json = objectMapper.writeValueAsString(cache);

        redis_service.savePollState(pollId, json);
        sendQuestion(pollId, 0);
    }

    public void stopPoll(String pollId) {
        // send with websocket to stop the poll
        redis_pub_service.publish_pollEnd(pollId);
    }

    public void sendQuestion(String pollId, int index) throws Exception {
        PollQuestionEntity question = redis_service.fetchQuestion(pollId, index);
        PollQuestionDTO questionDTO = new PollQuestionDTO(pollId, question);
        String question_message = objectMapper.writeValueAsString(questionDTO);
        rabbitMQ_service.sendQuestion(question_message);
        System.out.println("***question to send****\n" + question_message + "\n");

    }

    public void sendwait(String pollId) {
        redis_pub_service.publish_timesup(pollId);
        redis_service.removePollFromSchedule(pollId);
    }

    public void advanceToNextQuestion(String pollId) throws Exception {
        long now = System.currentTimeMillis();
        String json = redis_service.getPollState(pollId);
        if (json == null) {
            return;
        }
        PollCacheMetadata poll_metadata = objectMapper.readValue(json, PollCacheMetadata.class);
        if (poll_metadata.isEnded()) {
            redis_service.removePollFromSchedule(pollId);
            return;
        }
        int currentIndex = poll_metadata.getCurrentIndex();
        int total_questions = poll_metadata.getTotal_questions();

        if (currentIndex >= total_questions - 1) {
            stopPoll(pollId);
            poll_metadata.setEnded(true);
            redis_service.removePollFromSchedule(pollId);
            redis_service.savePollState(pollId, objectMapper.writeValueAsString(poll_metadata));
            System.out.println("POll ends.. did it pass??");
            return;
        }

        int next_index = currentIndex + 1;
        long QuestionTime = now + (redis_service.fetchQuestion(pollId, next_index).getTimeInSeconds() * 1000);
        poll_metadata.setCurrentEndingTimestamp(QuestionTime);
        poll_metadata.setCurrentIndex(next_index);
        poll_metadata.setCurrentEndingTimestamp(now);
        redis_service.updatePollSchedule(pollId, QuestionTime);
        redis_service.savePollState(pollId, objectMapper.writeValueAsString(poll_metadata));

        sendQuestion(pollId, next_index);

    }

}