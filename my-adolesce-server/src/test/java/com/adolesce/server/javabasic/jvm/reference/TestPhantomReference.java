package com.adolesce.server.javabasic.jvm.reference;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class TestPhantomReference {
    public static void main(String[] args) throws IOException, InterruptedException {
        //只要字符串对象被回收，当前的虚引用对象MyResource就会被加入到队列ReferenceQueue中去
        ReferenceQueue<String> queue = new ReferenceQueue<>();// 引用队列
        List<MyResource> list = new ArrayList<>();
        list.add(new MyResource(new String("a"), queue));
        list.add(new MyResource("b", queue));
        list.add(new MyResource(new String("c"), queue));

        System.gc(); // 垃圾回收（三个字符串对象被回收，当前的虚引用对象就会被加入到队列ReferenceQueue中去）
        Thread.sleep(100);
        Object ref;
        while ((ref = queue.poll()) != null) {
            if (ref instanceof MyResource) {
                ((MyResource)ref).clean();
            }
        }
    }

    static class MyResource extends PhantomReference<String> {
        public MyResource(String referent, ReferenceQueue<? super String> q) {
            super(referent, q);
        }
        // 释放外部资源的方法
        public void clean() {
            log.debug("clean");
        }
    }
}
