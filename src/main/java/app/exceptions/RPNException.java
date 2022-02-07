package app.exceptions;

public class RPNException extends RuntimeException {
    
    public RPNException(String message) {
        super(message);
    }

    public RPNException(Throwable cause) {
        super(cause);
    }

    public RPNException(String message, Throwable cause) {
        super(message, cause);
    }
}
