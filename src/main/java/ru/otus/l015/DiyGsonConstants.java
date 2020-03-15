package ru.otus.l015;

public enum DiyGsonConstants {
    JSON_OBJECT_LEFT_BRACKET("{"),
    JSON_OBJECT_RIGHT_BRACKET("}"),
    JSON_COMMA(","),
    JSON_COLON(":"),
    JSON_QOUTE("\""),
    JSON_THIS("this$0"),
    JSON_ARRAY_LEFT_BRACKET("["),
    JSON_ARRAY_RIGHT_BRACKET("]"),
    JSON_ARRAY("[]"),
    JSON_PRIMITIVE_TYPES("java.lang."),
    JSON_NULL("null");

    public String val;

    DiyGsonConstants(String val) {
        this.val = val;
    }
}
