package com.freeefly.restapiprac.advice.exception;

public class UserNotFountException extends RuntimeException{
    public UserNotFountException() {
        super();
    }

    public UserNotFountException(String message) {
        super(message);
    }

    public UserNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFountException(Throwable cause) {
        super(cause);
    }

    protected UserNotFountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
