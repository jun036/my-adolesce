package com.adolesce.server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2021/7/13 15:32
 */
public class SomeTest {
    @Test
    public void testInt1() {
        Integer i = new Integer(100);
        Integer j = new Integer(100);
        System.out.println(i == j);
    }

    @Test
    public void testInt2() {
        Integer i = new Integer(100);
        int j = 100;
        System.out.println(i == j);
    }

    @Test
    public void testInt3() {
        Integer i = new Integer(100);
        Integer j = 100;
        System.out.println(i == j);
    }

    @Test
    public void testInt4() {
        Integer i = 100;
        Integer j = 100;
        System.out.println(i == j);

        Integer x = 128;
        Integer y = 128;
        System.out.println(x == y);
    }

    @Test
    public void testString1() {
        String s = "hello";
        s = s + " world";
        System.out.println(s);
    }

    @Test
    public void testString2() {
        String s1 = "hello";
        String s2 = new String("hello");
        System.out.println(s1 == s2);
    }

    @Test
    public void testString3() {
        String s1 = new String("hello ") + new String("world");
        s1.intern();
        String s2 = "hello world";
        System.out.println(s1 == s2);
    }

    @Test
    public void testString4() {
        String s1 = new String("hello ") + new String("world");
        String s2 = "hello world";
        System.out.println("s1 == s2: " + (s1 == s2));

        String s3 = "hello " + "world";

        String h = "hello ";
        String w = "world";
        String s4 = h + w;

        String s5 = "hello";
        s5 = s5 + " world";

        System.out.println("s1 == s3: " + (s1 == s3));
        System.out.println("s1 == s4: " + (s1 == s4));

        System.out.println("s2 == s3: " + (s2 == s3));
        System.out.println("s2 == s4: " + (s2 == s4.intern()));
        System.out.println("s3 == s4: " + (s3 == s4.intern()));

        System.out.println("s1 == s5: " + (s1 == s5));
        System.out.println("s2 == s5: " + (s2 == s5));
        System.out.println("s3 == s5: " + (s3 == s5));
        System.out.println("s4 == s5: " + (s4 == s5));
    }

    @Test
    public void testTryFinally(){
        HashSet set = new HashSet<>();
        ArrayList list = new ArrayList();
        LinkedList list1 = new LinkedList();

        System.out.println("return :" +testReturn4());
    }

    private int testReturn1() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
        } finally {
            i++;
            System.out.println("finally:" + i);
        }
        return i;
    }

    private List<Integer> testReturn2() {
        List<Integer> list = new ArrayList<>();
        try {
            list.add(1);
            System.out.println("try:" + list);
            return list;
        } catch (Exception e) {
            list.add(2);
            System.out.println("catch:" + list);
        } finally {
            list.add(3);
            System.out.println("finally:" + list);
        }
        return list;
    }

    private int testReturn3() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            int x = i / 0;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
            return i;
        } finally {
            i++;
            System.out.println("finally:" + i);
        }
        return i;
    }

    private int testReturn4() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
            return i;
        } finally {
            i++;
            System.out.println("finally:" + i);
            return i;
        }
    }
}
