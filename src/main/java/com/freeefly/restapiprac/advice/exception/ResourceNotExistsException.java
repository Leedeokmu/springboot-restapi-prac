package com.freeefly.restapiprac.advice.exception;

public class ResourceNotExistsException extends RuntimeException{
    public ResourceNotExistsException() {
        super();
    }

    public ResourceNotExistsException(String message) {
        super(message);
    }

    public ResourceNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotExistsException(Throwable cause) {
        super(cause);
    }

    protected ResourceNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
