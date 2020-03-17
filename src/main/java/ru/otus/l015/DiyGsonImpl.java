package ru.otus.l015;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class DiyGsonImpl implements DiyGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        String res = "";

        if (src == null) {
            return DiyGsonConstants.JSON_NULL.val;
        } else {
            Class aClass = src.getClass();
            if (aClass.isArray()) {
                res += toJsonArray(src);
            } else if (src instanceof Collection) {
                res += toJsonCollection(src);
            } else if (src instanceof Number || src instanceof java.lang.Boolean
                    || src instanceof java.lang.Character || src instanceof java.lang.String) {
                res += toJsonPrimitive(src);
            } else {
                res += toJsonComplex(src, aClass);
            }
        }
        return res;
    }

    private String toJsonArray(Object src) throws IllegalAccessException {
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

    private String toJsonCollection(Object src) throws IllegalAccessException {
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

    private String toJsonPrimitive(Object src) {
        String res = "";

        if (src instanceof java.lang.String ||
                src instanceof java.lang.Character) {
            res += DiyGsonConstants.JSON_QOUTE.val + src + DiyGsonConstants.JSON_QOUTE.val;
        } else {
            res += src;
        }

        return res;
    }

    private String toJsonComplex(Object src, Class aClass) throws IllegalAccessException {
        String res = "";
        res = DiyGsonConstants.JSON_OBJECT_LEFT_BRACKET.val;
        for (Field currentField : aClass.getDeclaredFields()) {
            if (currentField.getName().equals(DiyGsonConstants.JSON_THIS.val)) {
            } else if (Modifier.isStatic(currentField.getModifiers())) {
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
