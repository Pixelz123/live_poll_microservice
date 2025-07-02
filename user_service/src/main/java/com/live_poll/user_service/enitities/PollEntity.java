package com.live_poll.user_service.enitities;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="polls")
public class PollEntity {
  @Id
  public String poll_id;
  public String poll_name;
  public String username;
  public List<PollQuestionEntity> question_set;
  public boolean status;
}