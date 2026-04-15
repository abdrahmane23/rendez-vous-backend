package com.rendezvous.demo.Exception;

public class ShiftAlreadyCanceledException extends RuntimeException {
    public ShiftAlreadyCanceledException(String message) {
        super(message);
    }

  public ShiftAlreadyCanceledException(String message, Throwable cause) {
    super(message, cause);
  }

  public ShiftAlreadyCanceledException(Throwable cause) {
    super(cause);
  }

  public ShiftAlreadyCanceledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ShiftAlreadyCanceledException() {
  }
}
