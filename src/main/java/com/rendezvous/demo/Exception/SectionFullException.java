package com.rendezvous.demo.Exception;

public class SectionFullException extends RuntimeException {
    public SectionFullException(String message) {
        super(message);
    }

    public SectionFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public SectionFullException(Throwable cause) {
        super(cause);
    }

    public SectionFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SectionFullException() {
    }
}
