package com.adolesce.server.javabasic.basic.reflex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2021/12/8 13:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsEntity extends GoodsParent {
    private GoodsEntity(String imgPath, Integer stock, String name, Double price) {
        super(imgPath, stock);
        this.name = name;
        this.price = price;
    }

    public String name;
    private Double price;
    private Integer code;

    public void printName() {
        System.out.println("子类打印名字：" + this.getName());
    }

    private void printPirce(String param) {
        System.out.println("子类打印价格：" + this.getPrice() + ",参数："+param);
    }


}
