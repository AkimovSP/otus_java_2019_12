package ru.otus.l015;


import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class DiyGsonImpl implements DiyGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        String res = "";

        Class aClass = src.getClass();
        res = "{";
        for (Field fff : aClass.getDeclaredFields()) {
            if (fff.getName().equals("this$0")) {
            } else {
                if (res != "{") {
                    res += ",";
                }
                res += "\"" + fff.getName() + "\":";
                fff.setAccessible(true);

                String simpleFieldTypeName = fff.getType().getSimpleName();
                if (simpleFieldTypeName.contains("[]")) {
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
                            res += "\"" + fff.get(src) + "\"";
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
        res += "}";

        return res;
    }

    private String toJsonArray(Object src, String simpleFieldTypeName, Field fff) throws IllegalAccessException {
        String res = "";
        res += "[";

        //не очень красиво, но по-другому не получилось
        if (simpleFieldTypeName.equals("String[]")) {
            res += arrayStringToJson(fff.get(src));
        } else if (simpleFieldTypeName.equals("int[]")) {
            res += arrayIntToJson(fff.get(src));
        } else {
            res += arrayAbstractToJson(fff.get(src));
        }

        res += "]";
        return res;
    }

    private String toJsonArrayList(Object src, String simpleFieldTypeName, Field fff) throws IllegalAccessException {
        String res = "";
        String separator = "";
        res += "[";

        ArrayList arr;
        arr = (ArrayList) fff.get(src);

        for (int i = 0; i < arr.size(); i++) {
            if (i > 0) {
                res += ",";
            }
            String typeNameElement = arr.get(i).getClass().getTypeName();
            if (typeNameElement.contains("java.lang.")) {
                String genericType = fff.getGenericType().getTypeName();
                if (genericType.contains("String")) {
                    separator = "\"";
                } else {
                    separator = "";
                }

                res += separator + arr.get(i).toString() + separator;
            } else {
                res += toJson(arr.get(i));
            }
        }
        res += "]";
        return res;
    }

    private String toJsonHashSet(Object src, String simpleFieldTypeName, Field fff) throws IllegalAccessException {
        String res = "";
        String separator = "";
        res += "[";

        HashSet hash;
        hash = (HashSet) fff.get(src);

        Iterator iterator = hash.iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            if (!isFirst) {
                res += ",";
            } else {
                isFirst = false;
            }

            Object currentItem = iterator.next();
            String typeNameElement = currentItem.getClass().getTypeName();
            if (typeNameElement.contains("java.lang.")) {
                String genericTypeHash = fff.getGenericType().getTypeName();
                if (genericTypeHash.contains("String")) {
                    separator = "\"";
                } else {
                    separator = "";
                }

                res += separator + currentItem + separator;
            } else {
                res += toJson(currentItem);
            }
        }

        res += "]";
        return res;
    }

    private String arrayIntToJson(Object arr) {
        String res = "";
        int[] simpleArray = (int[]) arr;

        for (int i = 0; simpleArray.length > i; i++) {
            if (i > 0) {
                res += ",";
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
                res += ",";
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
                res += ",";
            }

            res += "\"" + simpleArray[i] + "\"";
        }
        return res;
    }
}
