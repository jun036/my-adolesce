package com.adolesce.server.javabasic.jvm.outOfMemory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// -Xmx64m
// 模拟短信发送超时，但这时仍有大量的任务进入队列
@Slf4j
public class TestOomThreadPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        log.debug("begin...");
        while (true) {
            executor.submit(()->{
                try {
                    log.debug("send sms...");
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
