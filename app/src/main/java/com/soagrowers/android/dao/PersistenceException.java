package com.soagrowers.android.dao;

public class PersistenceException extends RuntimeException {

  public PersistenceException() {
  }

  public PersistenceException(String detailMessage) {
    super(detailMessage);
  }

  public PersistenceException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public PersistenceException(Throwable throwable) {
    super(throwable);
  }
}
