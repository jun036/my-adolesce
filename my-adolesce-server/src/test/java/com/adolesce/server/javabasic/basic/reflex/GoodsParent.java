package com.adolesce.server.javabasic.basic.reflex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/11/16 17:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsParent {
    private String imgPath;
    public Integer stock;

    private void printImgPath() {
        System.out.println("父类打印图片路径：" + this.getImgPath());
    }

    public void printStock(String param) {
        System.out.println("父类打印库存：" + this.getStock() + "，参数：" + param);
    }
}
