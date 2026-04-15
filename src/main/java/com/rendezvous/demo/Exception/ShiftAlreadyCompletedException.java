package com.rendezvous.demo.Exception;

public class ShiftAlreadyCompletedException extends RuntimeException {
    public ShiftAlreadyCompletedException(String message) {
        super(message);
    }

  public ShiftAlreadyCompletedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ShiftAlreadyCompletedException(Throwable cause) {
    super(cause);
  }

  public ShiftAlreadyCompletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ShiftAlreadyCompletedException() {
  }
}
