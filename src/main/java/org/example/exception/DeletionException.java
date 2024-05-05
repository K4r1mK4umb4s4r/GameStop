package org.example.exception;

public class DeletionException extends OperationException {
    public DeletionException(String message) {
        super(message);
    }

    public DeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
