package com.live_poll.user_communication_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live_poll.user_communication_service.dto.LeaderboardDTO;
import com.live_poll.user_communication_service.dto.PollQuestionEntity;
import com.live_poll.user_communication_service.dto.PollStatusDTO;
import com.live_poll.user_communication_service.dto.UserResponseDTO;
import com.live_poll.user_communication_service.services.EventPublisher;
import com.live_poll.user_communication_service.services.RedisPublishServices;
//    a poll have 2 channel (flow of frontend )
//     /topic/{pollId}   and     /admin/{pollId} 
//      audience side           presenter side 
//       sub and wait            sub and wait
//                            <--/start_request/{pollId}(OK)
//        <--question(OK)
//        -->/response
//                            --> leaderboard data 
//                            <--/proceed/{pollId}(OK)
@Controller
public class WebsocketController {
   public final ObjectMapper object_mapper;
    @Autowired
    SimpMessagingTemplate message_template;
    @Autowired
    EventPublisher rabbit_service_publisher;
    @Autowired
    RedisPublishServices redis_services;

    public WebsocketController(ObjectMapper object_mapper) {
        this.object_mapper = object_mapper;
    }

     
    // from admin side
    @MessageMapping("/start_request/{pollId}")
    public void handleStart(@DestinationVariable String pollId) {
        redis_services.publish_start(pollId);
    }
   // from admin side 
    @MessageMapping("/proceed/{pollId}")
    public void handleProceed(@DestinationVariable String pollId){
        redis_services.publish_proceed(pollId);
    }
    // from topic side
    @MessageMapping("/response")
    public void handleResponse(@Payload UserResponseDTO response) throws Exception {
        String responseJSON = object_mapper.writeValueAsString(response);
        System.out.println("response received :: stage 1\n");
        rabbit_service_publisher.sendForEvaluation(responseJSON);
    }
    // sends to /admin/{pollId}
    public void sendTimesUp(String pollId){
        PollStatusDTO proceedMessage= new PollStatusDTO("proceed");
        message_template.convertAndSend("/admin/"+pollId,proceedMessage);
    }

    // sends to /admin/{pollId}
    public void sendLeaderboard(LeaderboardDTO leaderboardDTO) {
        System.out.println("sending leaderboard....\n"+leaderboardDTO.scoreboard+"to "+"/admin/"+leaderboardDTO.pollId+"\n");
        message_template.convertAndSend("/admin/" + leaderboardDTO.pollId, leaderboardDTO);
    }
    //sends to /admin/{pollId}
    public void sendEndMessage(String pollId){
        PollStatusDTO endMessage=new PollStatusDTO("finished");
        message_template.convertAndSend("/admin/"+pollId,endMessage);
    }

    // sends to /poll/{pollId}
    public void sendQuestion(PollQuestionEntity question, String pollId) throws Exception {

        String json = object_mapper.writeValueAsString(question);
        System.out.println("SENDING OVER WEBSOCKET::" + json+" to "+"/topic/"+pollId+"\n");
        message_template.convertAndSend("/topic/" + pollId, json);

    }
  
}
