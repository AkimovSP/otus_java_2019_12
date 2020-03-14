package ru.otus.l009;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashSet;

class TestFramework {
    private static HashSet<String> methodsBefore = new HashSet<String>();
    private static HashSet<String> methodsAfter = new HashSet<String>();
    private static HashSet<String> methodsTest = new HashSet<String>();

    public static int errorTests;
    public static int okTests;

    public static void PrepareTests(String testClass) {
        System.out.println("Test framework preparation started - " + testClass);

        //Собираем аннотации и последовательности методов
        try {
            // Загружаем класс по имени
            Class cl = Class.forName(testClass);

            for (Method mmm : cl.getDeclaredMethods()) {
                boolean isBefore = false;
                boolean isAfter = false;
                boolean isTest = false;

                for (Annotation annotation : mmm.getDeclaredAnnotations()) {
                    isBefore = annotation.toString().contains("Before") | isBefore;
                    isAfter = annotation.toString().contains("After") | isAfter;
                    isTest = annotation.toString().contains("Test") | isTest;
                }
                if (isBefore) {
                    methodsBefore.add(mmm.getName());
                } else if (isAfter) {
                    methodsAfter.add(mmm.getName());
                } else if (isTest) {
                    methodsTest.add(mmm.getName());
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + testClass);
            return;
        }
        System.out.println("BEFORE METHODS "+methodsBefore);
        System.out.println("AFTER METHODS "+methodsAfter);
        System.out.println("TEST METHODS "+methodsTest);

        okTests = 0;
        errorTests = 0;

        System.out.println("Test framework preparation finished - " + testClass);
    }

    public static void RunTests(String testClass) {
        System.out.println("Test framework started - " + testClass);
        try {
            // Загружаем класс по имени
            Class cl = Class.forName(testClass);
            for (String testMethod : methodsTest) {
                //создаем объект
                Constructor<?> constructor =
                        cl.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object obj = constructor.newInstance();
                try {
                    System.out.println("-------------------------------");
                    System.out.println("START testing " + testMethod);
                    //Запускаем методы before
                    for (String beforeMethod : methodsBefore) {
                        Method mmmb = cl.getMethod(beforeMethod);
                        mmmb.invoke(obj);
                    }

                    //Запускаем тестовый метод
                    Method mmmt = cl.getMethod(testMethod);
                    mmmt.invoke(obj);

                    //Запускаем методы after
                    for (String afterMethod : methodsAfter) {
                        Method mmma = cl.getMethod(afterMethod);
                        mmma.invoke(obj);
                    }
                    System.out.println("FINISH testing " + testMethod);
                    okTests++;
                } catch (Exception e) {
                    System.out.println("TEST FAILED!!!");
                    errorTests++;
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Class not found " + testClass);
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("Test framework finished - " + testClass);
    }
}
