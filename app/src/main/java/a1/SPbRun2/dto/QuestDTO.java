package a1.SPbRun2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Misha on 17.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class QuestDTO {

    private int id;
    private String name;
    private String description;
    private int numberOfQuestions;

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
