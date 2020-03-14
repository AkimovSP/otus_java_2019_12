package ru.otus.l011;

public class Main {
    public static void main(String... args) throws NoSuchMethodException {
        TestLoggingInterface myClass = MyIoC.createMyClass();
        myClass.calculation(5);
        myClass.calculation_other(9);
    }
}
