package ru.otus.l011;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Main {
    public static void main(String... args) throws NoSuchMethodException {
        TestLoggingInterface myClass = MyIoC.createMyClass();
        myClass.calculation(5);
        myClass.calculation_other(9);
    }
}
