package com.live_poll.poll_service.entities;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="polls")
public class PollEntity {
  @Id
  private String poll_id;
  private String poll_name;
  private String username;
  private List<PollQuestionEntity> question_set;
  private boolean status;
}