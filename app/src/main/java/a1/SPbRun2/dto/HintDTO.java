package a1.SPbRun2.dto;

/**
 * Created by Misha on 22.11.2016.
 */
public class HintDTO {
    private String text;
    private int pointId;
    private int order;

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
