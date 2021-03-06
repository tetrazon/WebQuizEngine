package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {
    private static int idCounter = 1;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonProperty( value = "answer", access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> answer;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String text;
    @Size(min = 2)
    @NotNull
    @ElementCollection(targetClass = String.class)
    private List<String> options;
    @JsonProperty( value = "userName", access = JsonProperty.Access.WRITE_ONLY)
    private String userName;

    public Quiz(){

    }

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}