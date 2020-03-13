package ru.otus.l015;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class DiyGsonImpl implements DiyGson {

    @Override
    public String toJson(Object src) throws IllegalAccessException {
        String res = "";
        String separator = "";

        Class aClass = src.getClass();
        Type fieldType;
        res = "{";
        for (Field fff : aClass.getDeclaredFields()) {
            if (res != "{") {
                res += ",";
            }
            res += "\"" + fff.getName() + "\":";
            fff.setAccessible(true);

            String simpleFieldTypeName = fff.getType().getSimpleName();

            if (simpleFieldTypeName.contains("[]")) {
                res += "[";

                if (simpleFieldTypeName.equals("int[]")) {
                    res += arrayIntToJson(fff.get(src));
                } else if (simpleFieldTypeName.equals("String[]")) {
                    res += arrayStringToJson(fff.get(src));
                }

                res += "]";

            } else {
                switch (simpleFieldTypeName) {
                    case "ArrayList":
                        res += "[";

                        String genericType = fff.getGenericType().getTypeName();
                        if (genericType.contains("String")) {
                            separator = "\"";
                        } else {
                            separator = "";
                        }

                        ArrayList arr = new ArrayList();
                        arr = (ArrayList) fff.get(src);

                        for (int i = 0; i < arr.size(); i++) {
                            if (i > 0) {
                                res += ",";
                            }

                            res += separator + arr.get(i).toString() + separator;
                        }
                        res += "]";
                        break;
                    case "HashSet":
                        /*res += "[";

                    String genericTypeHash = fff.getGenericType().getTypeName();
                    if (genericTypeHash.contains("String")) {
                        separator = "\"";
                    } else {
                        separator = "";
                    }

                    HashSet hash= new HashSet();
                    hash = (HashSet) fff.get(src);

                    for (int i = 0; i < arr.size(); i++) {
                        if (i > 0) {
                            res += ",";
                        }

                        res += separator + hash.
                    }
*/
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
                //res += fff.getType().getSimpleName();

            }
        }
        res += "}";

        return res;
    }

    private static String arrayIntToJson(Object arr) {
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

    private static String arrayStringToJson(Object arr) {
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
