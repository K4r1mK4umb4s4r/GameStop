package org.example.exception;

public class InsertionException extends OperationException {
    public InsertionException(String message) {
        super(message);
    }

    public InsertionException(String message, Throwable cause) {
        super(message, cause);
    }
}
