package ru.otus.core.dao;

public class MyATMDaoException extends RuntimeException {
  public MyATMDaoException(Exception ex) {
    super(ex);
  }
}
