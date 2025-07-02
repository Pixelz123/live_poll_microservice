package com.live_poll.poll_service.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.live_poll.poll_service.entities.PollEntity;
import com.live_poll.poll_service.entities.PollQuestionEntity;
import com.live_poll.poll_service.repository.DbRepository;

@Service
public class Dbservices {
    @Autowired
    public DbRepository db_repo;

    public PollEntity getPoll(String poll_id) {
         Optional<PollEntity> poll=db_repo.findById(poll_id);
         if (poll.isPresent()){
            return poll.get();
         }
         else{
            return null;
         }
    }

    public void test(){
        PollEntity new_poll=new PollEntity();
        new_poll.setPoll_id("new_poll46");
        new_poll.setUsername("admin2");
        new_poll.setStatus(true);

        List<PollQuestionEntity> questionSet=new ArrayList<>();

        PollQuestionEntity question =new PollQuestionEntity();
        question.setCorrect_option(1);
        question.setQuestion_content("question_content");
        question.setQuestion_id("q1");
        String [] options={"1","2","3","4"};
        question.setOptions(options);
        question.setTimeInSeconds(4);

        PollQuestionEntity question2 =new PollQuestionEntity();
        question2.setCorrect_option(1);
        question2.setQuestion_content("question_content");
        question2.setQuestion_id("q2");
        String [] options2={"1","2","3","4"};
        question2.setOptions(options2);
        question2.setTimeInSeconds(8);

        PollQuestionEntity question3 =new PollQuestionEntity();
        question3.setCorrect_option(1);
        question3.setQuestion_content("question_content");
        question3.setQuestion_id("q3");
        String [] options3={"1","2","3","4"};
        question3.setOptions(options3);
        question3.setTimeInSeconds(15);
   
        questionSet.add(question);
        questionSet.add(question2);
        questionSet.add(question3);
        new_poll.setQuestion_set(questionSet);

       
         db_repo.save(new_poll);
        // System.out.println(saved_entity.getPoll_id());
    }
}