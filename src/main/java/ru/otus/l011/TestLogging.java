package ru.otus.l011;

public class TestLogging implements TestLoggingInterface {
    public String name = "AAA";

    @MyLog
    @Override
    public void calculation(int param) {
        System.out.println("inside calculation " + param);
    }

    //@MyLog
    @Override
    public void calculation_other(int param) {
        System.out.println("inside calculation_other " + param);
    }
}
