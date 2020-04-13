package com.freeefly.restapiprac.advice.exception;

public class EmailSigninFailedException extends RuntimeException{
    public EmailSigninFailedException() {
        super();
    }

    public EmailSigninFailedException(String message) {
        super(message);
    }

    public EmailSigninFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSigninFailedException(Throwable cause) {
        super(cause);
    }

    protected EmailSigninFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
