package com.rendezvous.demo.Exception;

public class AppointementExceededException extends RuntimeException {
    public AppointementExceededException(String message) {
        super(message);
    }

    public AppointementExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointementExceededException(Throwable cause) {
        super(cause);
    }

    public AppointementExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AppointementExceededException() {
    }
}
