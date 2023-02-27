package com.adolesce.server.javabasic.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyHandler implements InvocationHandler {
    /**
     * 目标对象
     */
    private final Object target;
    public DynamicProxyHandler(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("do something before");
        Object invoke = method.invoke(target, args);
        System.out.println("do something after");
        return invoke;
    }
}