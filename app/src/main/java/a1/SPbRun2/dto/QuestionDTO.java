package a1.SPbRun2.dto;

//сейчас это копия QuestionDTO с сервера
/**
 * Created by Misha on 17.11.2016.
 */
public class QuestionDTO {
    private String questionText;
    private double latitude;
    private double longitude;

    public QuestionDTO(){

    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
