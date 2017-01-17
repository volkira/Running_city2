package a1.SPbRun2.dto;

/**
 * Created by Misha on 16.11.2016.
 */
public class RegisterResult {
    private boolean result;
    private String message;

    public RegisterResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
