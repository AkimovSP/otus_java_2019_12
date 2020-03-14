package ru.otus.l011;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

class MyIoC {

    private static HashMap<String, Boolean> loggingParams = new HashMap<String, Boolean>();

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());

//Заполняем таблицу потребности в логировании
        Class<TestLogging> aClass = TestLogging.class;
        for (Method mmm : aClass.getDeclaredMethods()) {
            Boolean printLog = false;
            for (Annotation annotation : mmm.getDeclaredAnnotations()) {
                printLog = annotation.toString().contains("MyLog") | printLog;
            }
            loggingParams.put(mmm.getName(), printLog);
        }
//Заполняем таблицу потребности в логировании - закончили

        return (TestLoggingInterface) Proxy.newProxyInstance(MyIoC.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (loggingParams.get(method.getName())) {
                System.out.println("executed method: " + method.getName() + ", param: " + args[0]);
            }

            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }

}
