package com.adolesce.server.javabasic.jvm.outOfMemory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 需要修改堆内存大小参数：-Xmx64m
// 模拟短信发送超时，但这时仍有大量的任务进入队列
@Slf4j
public class TestOomThreadPool {
    public static void main(String[] args) {
        case3();
    }

    public static void case3(){
        case4();
        System.out.println("33333333333");
    }

    public static void case4(){
        case3();
        System.out.println("44444444444");
    }


    private static void case1() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        log.debug("begin...");
        while (true) {
            executor.submit(()->{
                try {
                    log.debug("send sms...");
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    static AtomicInteger c = new AtomicInteger();
    private static void case2() {
        ExecutorService executor = Executors.newCachedThreadPool();
        log.debug("begin...");
        while (true) {
            System.out.println(c.incrementAndGet());
            executor.submit(()->{
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
