package ru.otus.jdbc;

public interface SQLMapperJDBC {
    String createSQLInsert(Class paramClass);

    String createSQLUpdate(Class paramClass);

    String createSQLSelect(Class paramClass);
}
