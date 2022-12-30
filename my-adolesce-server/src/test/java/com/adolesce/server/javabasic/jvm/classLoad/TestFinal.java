package com.adolesce.server.javabasic.jvm.classLoad;

import java.io.IOException;

/**
 * 验证使用 final 修饰的【基本类型】变量不会触发类加载
 */
public class TestFinal {
    public static void main(String[] args) throws IOException {
        System.out.println(Student.c); // c 是 final static 基本类型
        System.in.read();

        System.out.println(Student.m); // m 是 final static 基本类型
        System.in.read();

        System.out.println(Student.n); // n 是 final static 引用类型
        System.in.read();
    }
}
