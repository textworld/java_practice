package cn.textworld.java.proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyTest {
    interface Hello {
        void sayHello();
    }

    static class ZhHello implements Hello{
        public void sayHello() {
            System.out.println("Hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler {
        Object originObject;

        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            System.out.println("Welcome");
            return null;
        }
    }
}
