package com.rendezvous.demo.Exception;

public class ExaminerNotAvailableException extends RuntimeException {
    public ExaminerNotAvailableException(String message) {
        super(message);
    }

  public ExaminerNotAvailableException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExaminerNotAvailableException(Throwable cause) {
    super(cause);
  }

  public ExaminerNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ExaminerNotAvailableException() {
  }
}
