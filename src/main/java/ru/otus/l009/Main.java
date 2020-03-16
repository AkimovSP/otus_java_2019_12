package ru.otus.l009;

public class Main {
    public static void main(String... args) {
        TestFramework test = new TestFramework();
        test.prepareTests("ru.otus.l009.TestClass");
        System.out.println("-------------------------------");
        test.runTests("ru.otus.l009.TestClass");
        System.out.println("-------------------------------");
        System.out.println("Test results - ok=" + test.okTests + ", error=" + test.errorTests);
    }
}
