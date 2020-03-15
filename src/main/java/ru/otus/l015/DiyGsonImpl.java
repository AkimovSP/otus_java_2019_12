package ru.otus.l015;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class DiyGsonImpl implements DiyGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        String res = "";

        if (src == null) {
            return DiyGsonConstants.JSON_NULL.val;
        } else {
            Class aClass = src.getClass();
            String simpleFieldTypeName = aClass.getTypeName();
            if (simpleFieldTypeName.contains(DiyGsonConstants.JSON_ARRAY.val)) {
                res += toJsonArray(src, simpleFieldTypeName);
            } else if (src instanceof Collection) {
                res += toJsonCollection(src, simpleFieldTypeName);
            } else if (simpleFieldTypeName.contains(DiyGsonConstants.JSON_PRIMITIVE_TYPES.val)) {
                res += toJsonPrimitive(src, simpleFieldTypeName);
            } else {
                res += toJsonComplex(src, aClass);
            }
        }
        return res;
    }

    private String toJsonArray(Object src, String simpleFieldTypeName) throws IllegalAccessException {
        String res = "";
        Object currentItem;
        res += DiyGsonConstants.JSON_ARRAY_LEFT_BRACKET.val;

        for (int i = 0; i < Array.getLength(src); i++) {
            if (i > 0) {
                res += DiyGsonConstants.JSON_COMMA.val;
            }
            currentItem = Array.get(src, i);
            res += toJson(currentItem);
        }
        res += DiyGsonConstants.JSON_ARRAY_RIGHT_BRACKET.val;
        return res;
    }

    private String toJsonCollection(Object src, String simpleFieldTypeName) throws IllegalAccessException {
        String res = "";
        res += DiyGsonConstants.JSON_ARRAY_LEFT_BRACKET.val;
        Iterator iterator = ((Collection) src).iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            if (!isFirst) {
                res += DiyGsonConstants.JSON_COMMA.val;
            } else {
                isFirst = false;
            }

            Object currentItem = iterator.next();
            res += toJson(currentItem);
        }
        res += DiyGsonConstants.JSON_ARRAY_RIGHT_BRACKET.val;
        return res;
    }

    private String toJsonPrimitive(Object src, String simpleFieldTypeName) {
        String res = "";
        switch (simpleFieldTypeName) {
            case "java.lang.String":
            case "java.lang.Character":
                res += DiyGsonConstants.JSON_QOUTE.val + src + DiyGsonConstants.JSON_QOUTE.val;
                break;
            case "java.lang.Integer":
            case "java.lang.Boolean":
            case "java.lang.Short":
            case "java.lang.Double":
            case "java.lang.Byte":
            case "java.lang.Float":
            case "java.lang.Long":
                res += src;
                break;
        }
        return res;
    }

    private String toJsonComplex(Object src, Class aClass) throws IllegalAccessException {
        String res = "";
        res = DiyGsonConstants.JSON_OBJECT_LEFT_BRACKET.val;
        for (Field currentField : aClass.getDeclaredFields()) {
            if (currentField.getName().equals(DiyGsonConstants.JSON_THIS.val)) {
            } else {
                if (res != DiyGsonConstants.JSON_OBJECT_LEFT_BRACKET.val) {
                    res += DiyGsonConstants.JSON_COMMA.val;
                }
                res += DiyGsonConstants.JSON_QOUTE.val + currentField.getName() + DiyGsonConstants.JSON_QOUTE.val + DiyGsonConstants.JSON_COLON.val;
                currentField.setAccessible(true);
                res += toJson(currentField.get(src));
            }
        }
        res += DiyGsonConstants.JSON_OBJECT_RIGHT_BRACKET.val;
        return res;
    }

}
