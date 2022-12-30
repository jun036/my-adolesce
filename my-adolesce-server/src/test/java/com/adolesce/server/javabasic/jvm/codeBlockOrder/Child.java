package com.adolesce.server.javabasic.jvm.codeBlockOrder;

/**
 * 类加载过程
 *  1、加载
 *      将类的字节码信息加载到方法区，将.class对象加载至堆内存
 *      方法区中的字节码是c++结构，无法通过java直接访问，必须通过.class类对象访问，即反射
 *  2、链接
 *      1、验证
 *          验证字节码是否符合JVM规范，验证合法性、安全性
 *      2、准备
 *          为static静态属性开辟空间，赋上默认值，除开static final修饰的变量，其他赋值语句不执行
 *      3、解析
 *          将常量池中的 符号引用 变为 直接引用
 *  3、初始化
 *      将静态代码块、非final修饰的静态属性合并为一个初始化方法，并且执行
 *
 *  执行过程
 *      1、父类-静态属性
 *      2、父类-静态代码块
 *      3、子类-静态属性
 *      4、子类-静态代码块
 *          1-4 是在类加载的初始化阶段执行
 *
 *      5、父类super -> 父类-非静态属性
 *      6、父类-非静态代码块
 *      7、父类—无参构造函数
 *      8、99
 *
 *      9、子类-非静态属性
 *      10、子类-非静态代码块
 *      11、子类-无参构造函数
 *      12、88
 *           5 - 12 遵循对象实例化过程（子类构造第一行super() -> 父类构造第一行super -> 父类成员属性赋值 -> 父类其他构造代码 ->
 *                                    子类成员属性赋值 -> 子类其他构造代码）
 */
class Parent {
    public static String PARENT_STATIC_FIELD = "父类-静态属性";
    public int a = 99;
    // 父类-静态块
    static {
        System.out.println(PARENT_STATIC_FIELD);
        System.out.println("父类-静态代码块");
    }

    public String parentField = "父类-非静态属性";

    // 父类-非静态块（构造代码块）
    {
        System.out.println(parentField);
        System.out.println("父类-非静态代码块");
    }

    public Parent() {
        System.out.println("父类—无参构造函数");
        System.out.println(a);
    }

}

public class Child extends Parent {
    public static String CHILD_STATIC_FIELD = "子类-静态属性";
    private int a = 88;

    //子类-静态块
    static {
        System.out.println(CHILD_STATIC_FIELD);
        System.out.println("子类-静态代码块");
    }

    public String field = "子类-非静态属性";

    // 非静态块
    {
        System.out.println(field);
        System.out.println("子类-非静态代码块");
    }

    public Child() {
        System.out.println("子类-无参构造函数");
        System.out.println(a);
    }

    public static void main(String[] args) {
        Child test1 = new Child();
    }

}