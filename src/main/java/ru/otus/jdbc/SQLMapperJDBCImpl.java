package ru.otus.jdbc;

import ru.otus.core.model.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SQLMapperJDBCImpl implements SQLMapperJDBC {

    @Override
    public String createSQLInsert(Class paramClass) {
        String fieldID = "";
        ArrayList<String> tableFields = new ArrayList<String>();
        boolean isID = false;
        String paramList = "";

        for (Field currentField : paramClass.getDeclaredFields()) {
            for (Annotation annotation : currentField.getDeclaredAnnotations()) {
                if (fieldID == "") {
                    if ((annotation.annotationType().getTypeName() == Id.class.getTypeName())) {
                        fieldID = currentField.getName();
                        isID = true;
                    }
                }
            }
            if (!isID) {
                tableFields.add(currentField.getName());
                if (paramList != "") {
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
    public String createSQLUpdate(Class paramClass) {
        String fieldID = "";
        String fieldsWithParams = "";
        ArrayList<String> tableFields = new ArrayList<String>();
        boolean isID = false;
        String paramList = "";

        for (Field currentField : paramClass.getDeclaredFields()) {
            for (Annotation annotation : currentField.getDeclaredAnnotations()) {
                if (fieldID == "") {
                    if ((annotation.annotationType().getTypeName() == Id.class.getTypeName())) {
                        fieldID = currentField.getName();
                        isID = true;
                    }
                }
            }
            if (!isID) {
                if (fieldsWithParams != "")
                {fieldsWithParams += ", ";}

                fieldsWithParams += currentField.getName();
                fieldsWithParams += " = ";
                fieldsWithParams += " ? ";
            }
            isID = false;
        }

        return "update  " + paramClass.getSimpleName()
                + " set "
                + fieldsWithParams
                + " where " + fieldID + " = ?";
    }

    @Override
    public String createSQLSelect(Class paramClass) {
        String fieldID = "";
        ArrayList<String> tableFields = new ArrayList<String>();
        String paramList = "";

        for (Field currentField : paramClass.getDeclaredFields()) {
            for (Annotation annotation : currentField.getDeclaredAnnotations()) {
                if (fieldID == "") {
                    if ((annotation.annotationType().getTypeName() == Id.class.getTypeName())) {
                        fieldID = currentField.getName();
                    }
                }
            }
            tableFields.add(currentField.getName());
            if (paramList != "") {
                paramList += ",";
            }
            paramList += "?";

        }

        return "select " + tableFields.toString().substring(1, tableFields.toString().length() - 1) + " from " +
                paramClass.getSimpleName() + " where " +
                fieldID + " = ?";
    }
}
