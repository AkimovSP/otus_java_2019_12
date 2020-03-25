package ru.otus.jdbc;

import ru.otus.core.model.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SQLMapperJDBCImpl implements SQLMapperJDBC {

    @Override
    public String createSQLInsert(Class<?> paramClass) {
        ArrayList<String> tableFields = new ArrayList<String>();
        boolean isID = false;
        String paramList = "";

        for (Field currentField : paramClass.getDeclaredFields()) {
            for (Annotation annotation : currentField.getDeclaredAnnotations()) {
                if ((annotation.annotationType().getTypeName().equals(Id.class.getTypeName()))) {
                    isID = true;
                }
            }
            if (!isID) {
                tableFields.add(currentField.getName());
                if (!paramList.equals("")) {
                    paramList += ",";
                }
                paramList += "?";
            }
            isID = false;

        }
        return "insert into " + paramClass.getSimpleName()
                + "(" + tableFields.toString().substring(1, tableFields.toString().length() - 1) +
                ") values (" + paramList + ")";
    }

    @Override
    public String createSQLUpdate(Class<?> paramClass) throws SQLMapperException {
        String fieldID = "";
        String fieldsWithParams = "";
        boolean isID = false;

        for (Field currentField : paramClass.getDeclaredFields()) {
            for (Annotation annotation : currentField.getDeclaredAnnotations()) {
                if (fieldID.equals("")) {
                    if ((annotation.annotationType().getTypeName().equals(Id.class.getTypeName()))) {
                        fieldID = currentField.getName();
                        isID = true;
                    }
                }
            }
            if (!isID) {
                if (!fieldsWithParams.equals("")) {
                    fieldsWithParams += ", ";
                }
                fieldsWithParams += currentField.getName();
                fieldsWithParams += " = ?";
            }
            isID = false;
        }

        if (fieldID.equals("")) {
            throw new SQLMapperException("No ID field in class");
        } else {
            return "update  " + paramClass.getSimpleName()
                    + " set "
                    + fieldsWithParams
                    + " where " + fieldID + " = ?";
        }
    }

    @Override
    public String createSQLSelect(Class<?> paramClass) {
        String fieldID = "";
        ArrayList<String> tableFields = new ArrayList<String>();
        String paramList = "";

        for (Field currentField : paramClass.getDeclaredFields()) {
            for (Annotation annotation : currentField.getDeclaredAnnotations()) {
                if (fieldID.equals("")) {
                    if ((annotation.annotationType().getTypeName().equals(Id.class.getTypeName()))) {
                        fieldID = currentField.getName();
                    }
                }
            }
            tableFields.add(currentField.getName());
            if (!paramList.equals("")) {
                paramList += ",";
            }
            paramList += "?";
        }

        return "select " + tableFields.toString().substring(1, tableFields.toString().length() - 1) + " from " +
                paramClass.getSimpleName() + " where " +
                fieldID + " = ?";
    }
}
