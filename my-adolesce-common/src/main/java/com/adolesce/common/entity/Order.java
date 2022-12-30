package com.adolesce.common.entity;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/3/15 12:03
 */
@Data
public class Order {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 订单商品
     */
    private Goods goods;

    public static void main(String[] args) {
        Integer a = 1;
        fuzhi(a);
        System.out.println(a);

        /*Order order1 = new Order();
        order1.setOrderName("订单1");
        disposeOrder(order1);
        System.out.println(order1.getOrderName());*/


        /*Order A = new Order();
        A.setOrderName("A");
        Order B = new Order();
        B.setOrderName("B");
        System.out.println("交换前orderName：" + A + "-->" + B);
        swap(A,B);
        System.out.println("交换后orderName：" + A + "-->" + B);*/
    }

    private static void fuzhi(Integer a) {
        a = 2;
    }

    public static void swap(Order a, Order b) {
        Order tmp = a;
        a = b;
        b = tmp;
        a.setOrderName("C");
        b.setOrderName("D");
    }

    public static void disposeOrder(Order order){
        order.setOrderName("订单2");
    }
}
