package com.adolesce.server.javabasic.designPattern;

import java.lang.reflect.Proxy;

/**
 *
 /**
 * （1）创建型模式：单例模式、抽象工厂模式、建造者模式、工厂模式、原型模式。
 *
 * （2）结构型模式：适配器模式、桥接模式、装饰模式、组合模式、外观模式、享元模式、代理模式。
 *
 * （3）行为型模式：模版方法模式、命令模式、迭代器模式、观察者模式、中介者模式、备忘录模式、解释器模式、状态模式、策略模式、职责链模式、访问者模式。
 */
public class ProxyTest {
    public static void main(String[] args) {
        //1、创建被代理的目标对象
        Target target = new Target();
        //2、创建代理类处理器对象
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(target);
        //3、创建代理类对象
        //a.JDK会通过传入的参数信息动态地在内存中创建和.class文件等同的字节码
        //b.然后会根据相应的字节码转换成对应的class
        //c.最后创建代理类实例
        TargetInterface proxy = (TargetInterface) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), dynamicProxyHandler);
        System.out.println("doSomething() call: " + proxy.doSomething());
        System.out.println("------------------------");
        System.out.println("proxy.printNum() call: " + proxy.printNum());
    }
}