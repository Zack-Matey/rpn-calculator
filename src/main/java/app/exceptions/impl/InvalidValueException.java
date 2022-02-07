package app.exceptions.impl;

import app.exceptions.RPNException;

public class InvalidValueException extends RPNException {
    
    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(Throwable cause) {
        super(cause);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
