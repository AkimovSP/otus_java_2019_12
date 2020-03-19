package ru.otus.l009;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;

class TestFramework {
    private Set<Method> methodsAfter = new HashSet<Method>();
    private Set<Method> methodsTest = new HashSet<Method>();
    private Set<Method> methodsBefore = new HashSet<Method>();

    private int errorTests;
    private int okTests;

    private Class currentClass;

    public void prepareTests(String testClass) {
        System.out.println("Test framework preparation started - " + testClass);

        //Собираем аннотации и последовательности методов
        try {
            // Загружаем класс по имени
            currentClass = Class.forName(testClass);

            for (Method mmm : currentClass.getDeclaredMethods()) {
                boolean isBefore = false;
                boolean isAfter = false;
                boolean isTest = false;

                for (Annotation annotation : mmm.getDeclaredAnnotations()) {
                    isBefore = (annotation.annotationType().getTypeName() == Before.class.getTypeName()) | isBefore;
                    isAfter = (annotation.annotationType().getTypeName() == After.class.getTypeName()) | isAfter;
                    isTest = (annotation.annotationType().getTypeName() == Test.class.getTypeName()) | isTest;
                }
                if (isBefore) {
                    methodsBefore.add(mmm);
                } else if (isAfter) {
                    methodsAfter.add(mmm);
                } else if (isTest) {
                    methodsTest.add(mmm);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + testClass);
            return;
        }
        System.out.println("BEFORE METHODS " + methodsBefore);
        System.out.println("AFTER METHODS " + methodsAfter);
        System.out.println("TEST METHODS " + methodsTest);

        okTests = 0;
        errorTests = 0;

        System.out.println("Test framework preparation finished - " + testClass);
    }

    public void runTests(String testClass) {
        System.out.println("Test framework started - " + testClass);
        try {
            for (Method testMethod : methodsTest) {
                //создаем объект
                Constructor<?> constructor =
                        currentClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object obj = constructor.newInstance();
                boolean beforeFailed = false;
                boolean isFailed = false;

                System.out.println("-------------------------------");
                System.out.println("START testing " + testMethod);
                //Запускаем методы before
                for (Method beforeMethod : methodsBefore) {
                    try {
                        beforeMethod.invoke(obj);
                    } catch (Exception e) {
                        System.out.println("TEST BEFORE FAILED!!! " + beforeMethod.getName());
                        beforeFailed = true;
                        isFailed = true;
                        break;
                    }
                }

                //Запускаем основной метод
                try {
                    if (!beforeFailed) {
                        testMethod.invoke(obj);
                    }
                } catch (Exception e) {
                    isFailed = true;
                    System.out.println("TEST main FAILED!!! " + testMethod.getName());
                }

                //Запускаем методы after
                if (!beforeFailed) {
                    for (Method afterMethod : methodsAfter) {
                        try {
                            afterMethod.invoke(obj);
                        } catch (Exception e) {
                            System.out.println("TEST AFTER FAILED!!! " + afterMethod.getName());
                            isFailed = true;
                        }
                    }
                }

                //Подводим итог
                if (!isFailed) {
                    System.out.println("FINISH testing " + testMethod);
                    okTests++;
                } else {
                    System.out.println("TEST FAILED!!!");
                    errorTests++;
                }
            }
        } catch (NoSuchMethodException e) {
            System.out.println("Class not found " + testClass);
            return;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("Test framework finished - " + testClass);
    }

    public int getErrorTests() {
        return errorTests;
    }

    public int getOkTests() {
        return okTests;
    }
}
