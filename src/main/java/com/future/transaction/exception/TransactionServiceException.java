package com.future.transaction.exception;

public class TransactionServiceException extends Exception {

    private String message = null;

    public TransactionServiceException() {
    }

    public TransactionServiceException(String message) {
        super(message);
        this.message = message;
    }

    public TransactionServiceException(Throwable cause) {
        super(cause);
    }

    public String toString() {
        return this.message;
    }

    public String getMessage() {
        return this.message;
    }
}
