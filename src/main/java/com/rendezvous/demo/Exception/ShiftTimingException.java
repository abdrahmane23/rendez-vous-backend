package com.rendezvous.demo.Exception;

public class ShiftTimingException extends RuntimeException {
    public ShiftTimingException(String message) {
        super(message);
    }

    public ShiftTimingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiftTimingException(Throwable cause) {
        super(cause);
    }

    public ShiftTimingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShiftTimingException() {
    }
}
