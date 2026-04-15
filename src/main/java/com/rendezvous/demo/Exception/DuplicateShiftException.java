package com.rendezvous.demo.Exception;

public class DuplicateShiftException extends RuntimeException {
    public DuplicateShiftException() {
    }

    public DuplicateShiftException(String message) {
        super(message);
    }

    public DuplicateShiftException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateShiftException(Throwable cause) {
        super(cause);
    }

    public DuplicateShiftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
