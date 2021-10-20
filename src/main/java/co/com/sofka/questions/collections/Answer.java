package co.com.sofka.questions.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Answer {
    @Id
    private String id;
    private String userId;
    private String questionId;
    private String answer;
    private Integer position;
    private List<String> upVotes;
    private List<String> downVotes;

    public Answer(){
        this.upVotes = new ArrayList<>();
        this.downVotes = new ArrayList<>();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getUpVotes() {
        return upVotes;
    }
    public void setUpVotes(List<String> upVotes) {
        this.upVotes = upVotes;
    }

    public void addUpVote(String useId)
    {
        this.upVotes.add(useId);
    }

    public void removeUpVote(String userId)
    {
        this.upVotes.remove(userId);
    }

    public List<String> getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(List<String> downVotes) {
        this.downVotes = downVotes;
    }

    public void addDownVote(String useId)
    {
        this.downVotes.add(useId);
    }

    public void removeDownVote(String userId)
    {
        this.downVotes.remove(userId);
    }
}
