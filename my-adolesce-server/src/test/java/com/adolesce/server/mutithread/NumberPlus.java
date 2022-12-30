package com.adolesce.server.mutithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/11/10 11:33
 */
public class NumberPlus implements Runnable{
    private static int i = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private CountDownLatch countDownLatch;

    public NumberPlus(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread thread1 = new Thread(new NumberPlus(countDownLatch));
        Thread thread2 = new Thread(new NumberPlus(countDownLatch));
        thread1.start();
        thread2.start();
        countDownLatch.await();
        System.out.println(NumberPlus.i);
        System.out.println(NumberPlus.atomicInteger.get());
    }

    @Override
    public void run() {
        for (int j = 0; j < 5000 ; j++) {
            synchronized (NumberPlus.class){
                i++;
            }
            atomicInteger.incrementAndGet();
        }
        countDownLatch.countDown();
    }
}

//5147
//5054
//8032
//10000
//7099
//7389
//8453
