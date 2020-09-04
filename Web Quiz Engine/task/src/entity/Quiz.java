package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
public class Quiz {
    private static int idCounter = 1;
    private int id;
    @JsonProperty( value = "answer", access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;
    @NotNull
    @NotEmpty
    private String Title;
    @NotNull
    @NotEmpty
    private String text;
    @Size(min = 2)
    @NotNull
    private List<String> options;

    public Quiz(){
        id = idCounter++;
    }

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this();
        Title = title;
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
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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
}
