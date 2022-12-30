package com.adolesce.server.javabasic.jvm.objInstanceOrder;

/**
 * 1、为该对象实例在堆内存中开辟空间，并为对象所有成员属性赋上默认初始值
 *
 * 2、调用子类构造函数第一行的super方法（即使不写默认也会执行） 即：调用父类构造方法
 *
 * 3、父类构造方法中调用自己的super方法
 *
 * 4、当父类调用super完成，显示的为父类成员属性赋值
 *
 * 5、继续执行父类构造方法其他代码
 *
 * 6、子类调用super()完成，显示的为子类成员属性赋值
 *
 * 7、继续执行子类构造方法其他代码
 *
 */
public class MyTest {
    public static void main(String[] args) {
        Child child = new Child();
    }
}
