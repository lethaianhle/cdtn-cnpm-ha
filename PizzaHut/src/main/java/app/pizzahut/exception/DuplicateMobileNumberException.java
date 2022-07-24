package app.pizzahut.exception;

public class DuplicateMobileNumberException extends RuntimeException {

    public DuplicateMobileNumberException() {
        super();
    }

    public DuplicateMobileNumberException(String message) {
        super(message);
    }

}
