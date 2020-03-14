package ru.otus.l015;


import java.lang.reflect.Field;
import java.util.*;

public class DiyGsonImpl implements DiyGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        String res = "";

        Class aClass = src.getClass();
        res = DiyGsonConstants.JSON_OBJECT_LEFT_BRACKET.val;
        for (Field fff : aClass.getDeclaredFields()) {
            if (fff.getName().equals(DiyGsonConstants.JSON_THIS.val)) {
            } else {
                if (res != DiyGsonConstants.JSON_OBJECT_LEFT_BRACKET.val) {
                    res += DiyGsonConstants.JSON_COMMA.val;
                }
                res += DiyGsonConstants.JSON_QOUTE.val + fff.getName() + DiyGsonConstants.JSON_QOUTE.val + DiyGsonConstants.JSON_COLON.val;
                fff.setAccessible(true);

                String simpleFieldTypeName = fff.getType().getSimpleName();
                if (simpleFieldTypeName.contains(DiyGsonConstants.JSON_ARRAY.val)) {
                    res += toJsonArray(src, simpleFieldTypeName, fff);
                } else {
                    switch (simpleFieldTypeName) {
                        case "ArrayList":
                            res += toJsonArrayList(src, simpleFieldTypeName, fff);
                            break;
                        case "HashSet":
                            res += toJsonHashSet(src, simpleFieldTypeName, fff);
                            break;
                        case "String":
                        case "char":
                            res += DiyGsonConstants.JSON_QOUTE.val + fff.get(src) + DiyGsonConstants.JSON_QOUTE.val;
                            break;
                        case "int":
                        case "boolean":
                        case "short":
                        case "double":
                        case "byte":
                        case "float":
                        case "long":
                            res += fff.get(src);
                            break;
                    }
                }
            }
        }
        res += DiyGsonConstants.JSON_OBJECT_RIGHT_BRACKET.val;
        return res;
    }

    private String toJsonArray(Object src, String simpleFieldTypeName, Field fff) throws IllegalAccessException {
        String res = "";
        res += DiyGsonConstants.JSON_ARRAY_LEFT_BRACKET.val;

        //не очень красиво, но по-другому не получилось
        if (simpleFieldTypeName.equals(DiyGsonConstants.JSON_STRING_ARRAY.val)) {
            res += arrayStringToJson(fff.get(src));
        } else if (simpleFieldTypeName.equals(DiyGsonConstants.JSON_INT_ARRAY.val)) {
            res += arrayIntToJson(fff.get(src));
        } else {
            res += arrayAbstractToJson(fff.get(src));
        }
        res += DiyGsonConstants.JSON_ARRAY_RIGHT_BRACKET.val;
        return res;
    }

    private String toJsonArrayList(Object src, String simpleFieldTypeName, Field fff) throws IllegalAccessException {
        String res = "";
        String separator = "";
        res += DiyGsonConstants.JSON_ARRAY_LEFT_BRACKET.val;

        ArrayList arr;
        arr = (ArrayList) fff.get(src);

        for (int i = 0; i < arr.size(); i++) {
            if (i > 0) {
                res += DiyGsonConstants.JSON_COMMA.val;
            }
            String typeNameElement = arr.get(i).getClass().getTypeName();
            if (typeNameElement.contains(DiyGsonConstants.JSON_PRIMITIVE_TYPES.val)) {
                String genericType = fff.getGenericType().getTypeName();
                if (genericType.contains(DiyGsonConstants.JSON_STRING.val)) {
                    separator = DiyGsonConstants.JSON_QOUTE.val;
                } else {
                    separator = "";
                }

                res += separator + arr.get(i).toString() + separator;
            } else {
                res += toJson(arr.get(i));
            }
        }
        res += DiyGsonConstants.JSON_ARRAY_RIGHT_BRACKET.val;
        return res;
    }

    private String toJsonHashSet(Object src, String simpleFieldTypeName, Field fff) throws IllegalAccessException {
        String res = "";
        String separator = "";
        res += DiyGsonConstants.JSON_ARRAY_LEFT_BRACKET.val;

        HashSet hash;
        hash = (HashSet) fff.get(src);

        Iterator iterator = hash.iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            if (!isFirst) {
                res += DiyGsonConstants.JSON_COMMA.val;
            } else {
                isFirst = false;
            }

            Object currentItem = iterator.next();
            String typeNameElement = currentItem.getClass().getTypeName();
            if (typeNameElement.contains(DiyGsonConstants.JSON_PRIMITIVE_TYPES.val)) {
                String genericTypeHash = fff.getGenericType().getTypeName();
                if (genericTypeHash.contains(DiyGsonConstants.JSON_STRING.val)) {
                    separator = DiyGsonConstants.JSON_QOUTE.val;
                } else {
                    separator = "";
                }

                res += separator + currentItem + separator;
            } else {
                res += toJson(currentItem);
            }
        }
        res += DiyGsonConstants.JSON_ARRAY_RIGHT_BRACKET.val;
        return res;
    }

    private String arrayIntToJson(Object arr) {
        String res = "";
        int[] simpleArray = (int[]) arr;

        for (int i = 0; simpleArray.length > i; i++) {
            if (i > 0) {
                res += DiyGsonConstants.JSON_COMMA.val;
            }
            res += simpleArray[i];
        }
        return res;
    }

    private String arrayAbstractToJson(Object arr) throws IllegalAccessException {
        String res = "";
        Object[] simpleArray = (Object[]) arr;

        for (int i = 0; simpleArray.length > i; i++) {
            if (i > 0) {
                res += DiyGsonConstants.JSON_COMMA.val;
            }
            res += toJson(simpleArray[i]);
        }
        return res;
    }

    private String arrayStringToJson(Object arr) {
        String res = "";
        String[] simpleArray = (String[]) arr;

        for (int i = 0; simpleArray.length > i; i++) {
            if (i > 0) {
                res += DiyGsonConstants.JSON_COMMA.val;
            }

            res += DiyGsonConstants.JSON_QOUTE.val + simpleArray[i] + DiyGsonConstants.JSON_QOUTE.val;
        }
        return res;
    }
}
