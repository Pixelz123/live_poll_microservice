package  com.example.leaderboard_service.entity;



import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class PollEntity {
  @Id
  private String poll_id;
  private String user_id;
  private List<PollQuestionEntity> question_set;
  private boolean status; 
}
