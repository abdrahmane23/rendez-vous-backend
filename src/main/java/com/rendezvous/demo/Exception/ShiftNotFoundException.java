package com.rendezvous.demo.Exception;


public class ShiftNotFoundException extends RuntimeException {
    public ShiftNotFoundException(String message) {
        super(message);
    }

    public ShiftNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiftNotFoundException(Throwable cause) {
        super(cause);
    }

    public ShiftNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShiftNotFoundException() {
    }
}
