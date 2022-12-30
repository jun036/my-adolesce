package com.adolesce.server.javabasic.jvm.classLoad;

import java.io.IOException;

/**
 * 验证类加载 链接阶段的解析阶段 将常量池的符号引用变为直接引用
 */
public class TestResolution {

    static class A {
        static {
            System.out.println("init A");
        }
    }

    static class B {
        static {
            System.out.println("init B");
        }
    }

    static class C {
        static {
            System.out.println("init C");
        }
    }

    public static void main(String[] args) throws IOException {
        System.in.read();
        A a = new A();
        System.in.read();
        B b = new B();
        System.in.read();
        C c = new C();
        System.in.read();
    }

}
