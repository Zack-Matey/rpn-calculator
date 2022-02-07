package app.exceptions.impl;

import app.exceptions.RPNException;

public class StackException extends RPNException {

    public StackException(String message) {
        super(message);
    }

    public StackException(Throwable cause) {
        super(cause);
    }

    public StackException(String message, Throwable cause) {
        super(message, cause);
    }
}
