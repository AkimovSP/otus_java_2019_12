package ru.otus.jdbc;

public interface SQLMapperJDBC {
    String createSQLInsert(Class<?> paramClass);

    String createSQLUpdate(Class<?> paramClass) throws SQLMapperException;

    String createSQLSelect(Class<?> paramClass);
}
