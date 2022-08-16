package tum.auth.api;

public class ErrorResponse {

    private String message;

    public ErrorResponse() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
