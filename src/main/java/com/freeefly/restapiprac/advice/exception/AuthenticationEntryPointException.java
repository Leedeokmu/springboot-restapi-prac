package com.freeefly.restapiprac.advice.exception;

public class AuthenticationEntryPointException extends RuntimeException{

    public AuthenticationEntryPointException() {
        super();
    }

    public AuthenticationEntryPointException(String message) {
        super(message);
    }

    public AuthenticationEntryPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationEntryPointException(Throwable cause) {
        super(cause);
    }

    protected AuthenticationEntryPointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
