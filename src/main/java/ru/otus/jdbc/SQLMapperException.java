package ru.otus.jdbc;

public class SQLMapperException extends RuntimeException {
  public SQLMapperException(String msg) {
    super(msg);
  }

  public SQLMapperException(Exception ex) {
    super(ex);
  }
}
