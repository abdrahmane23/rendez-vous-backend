package com.rendezvous.demo.Exception;

public class AppointementTimingException extends RuntimeException {
    public AppointementTimingException(String message) {
        super(message);
    }

  public AppointementTimingException(String message, Throwable cause) {
    super(message, cause);
  }

  public AppointementTimingException(Throwable cause) {
    super(cause);
  }

  public AppointementTimingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public AppointementTimingException() {
  }
}
