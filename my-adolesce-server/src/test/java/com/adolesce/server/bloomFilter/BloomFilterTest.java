package com.adolesce.server.bloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

import static com.adolesce.server.bloomFilter.BloomFileter.MisjudgmentRate.HIGH;

/**
 * @version 1.0
 * @description: 布隆过滤器测试类
 */
public class BloomFilterTest {
    /**
     * 测试自定义的布隆过滤器
     */
    @Test
    public void test1() {
        BloomFileter fileter = new BloomFileter(7);
        System.out.println(fileter.addIfNotExist("abc"));
        System.out.println(fileter.addIfNotExist("abcd"));
        System.out.println(fileter.addIfNotExist("abcde"));
        System.out.println(fileter.addIfNotExist("11111"));
        System.out.println(fileter.addIfNotExist("22222"));
        System.out.println(fileter.addIfNotExist("33333"));

        System.out.println(fileter.addIfNotExist("abc"));
        System.out.println(fileter.addIfNotExist("33333"));

        //将过滤器当前状态保存至文件，以备后续使用
        fileter.saveFilterToFile("D:\\11.obj");
        //将文件中的数据恢复成布隆过滤器(里面有之前过滤的很多数据记录)
        fileter = BloomFileter.readFilterFromFile("D:\\11.obj");
        System.out.println(fileter.getUseRate());
        System.out.println(fileter.addIfNotExist("44444"));
        System.out.println(fileter.addIfNotExist("abcde"));
    }

    /**
     * 测试自定义的布隆过滤器误判率
     */
    @Test
    public void test2() {
        int dataCount = 1000000;//预计要插入多少数据
        BloomFileter.MisjudgmentRate fpp = HIGH;//期望的误判率

        BloomFileter fileter = new BloomFileter(fpp,dataCount,null);

        //插入数据
        for (int i = 0; i < 1000000; i++) {
            fileter.addIfNotExist(String.valueOf(i));
        }
        int count = 0;
        for (int i = 2000000; i < 3000000; i++) {
            boolean flag =  fileter.addIfNotExist(String.valueOf(i));
            if (!flag) {
                count++;
                System.out.println(i + "误判了");
            }
        }
        System.out.println("总共的误判数:" + count);
    }


    /**
     * 测试谷歌布隆过滤器
     */
    @Test
    public void test3() throws IOException {
        int dataCount = 10;//预计要插入多少数据
        double fpp = 0.001;//期望的误判率
        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), dataCount, fpp);

        System.out.println(filter.put("abc"));
        System.out.println(filter.put("abcd"));
        System.out.println(filter.put("abcde"));
        System.out.println(filter.put("11111"));
        System.out.println(filter.put("22222"));
        System.out.println(filter.put("33333"));

        System.out.println(filter.put("abc"));
        System.out.println(filter.put("33333"));

        //将过滤器当前状态保存至文件，以备后续使用
        OutputStream oos = new FileOutputStream("D:\\22.obj");
        filter.writeTo(oos);

        //将文件中的数据恢复成布隆过滤器(里面有之前过滤的很多数据记录)
        InputStream ois = new FileInputStream("D:\\22.obj");
        BloomFilter<String> bloomFilter = BloomFilter.readFrom(ois, Funnels.stringFunnel(Charset.forName("UTF-8")));

        System.out.println(bloomFilter.put("44444"));
        System.out.println(bloomFilter.put("abcde"));


        System.out.println(bloomFilter.mightContain("44444"));
    }

    /**
     * 测试谷歌布隆过滤器误判率
     */
    @Test
    public void test4() {
        int dataCount = 1000000;//预计要插入多少数据
        double fpp = 0.00000000000001;//期望的误判率

        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), dataCount, fpp);
        //插入数据
        for (int i = 1; i <= 1000000; i++) {
            bloomFilter.put(String.valueOf(i));
        }
        int count = 0;
        for (int i = 1000001; i <= 2000000; i++) {
            if (bloomFilter.mightContain(String.valueOf(i))) {
                count++;
                System.out.println(i + "误判了");
            }
        }
        System.out.println("总共的误判数:" + count);
    }
}
