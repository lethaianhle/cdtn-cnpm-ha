package app.pizzahut.exception;

public class RankNotFoundException extends RuntimeException {

    public RankNotFoundException() {
        super();
    }

    public RankNotFoundException(String message) {
        super(message);
    }

}
