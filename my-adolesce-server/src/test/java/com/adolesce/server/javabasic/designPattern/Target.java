package com.adolesce.server.javabasic.designPattern;

public class Target implements TargetInterface {
    @Override
    public String doSomething() {
        System.out.println("doSomething");
        return "doSomething";
    }
    @Override
    public int printNum() {
        System.out.println("printNum");
        return 100;
    }
}