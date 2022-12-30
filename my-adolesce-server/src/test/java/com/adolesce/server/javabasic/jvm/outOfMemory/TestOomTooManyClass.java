package com.adolesce.server.javabasic.jvm.outOfMemory;

import groovy.lang.GroovyShell;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

// -XX:MaxMetaspaceSize=24m
// 模拟不断生成类, 但类无法卸载的情况 (可以使用jconsole工具去验证)
public class TestOomTooManyClass {

    //static GroovyShell shell = new GroovyShell();

    public static void main(String[] args) {
        AtomicInteger c = new AtomicInteger();
        while (true) {
            try (FileReader reader = new FileReader("D://some-test-file/txt/1.txt")) {
                GroovyShell shell = new GroovyShell();
                shell.evaluate(reader);
                System.out.println(c.incrementAndGet());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
