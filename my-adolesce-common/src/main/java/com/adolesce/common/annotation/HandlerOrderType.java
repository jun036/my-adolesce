package com.adolesce.common.annotation;

import com.adolesce.common.enums.OrderType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)  //作用在类上
@Retention(RetentionPolicy.RUNTIME)
@Inherited //子类可以继承此注解
public @interface HandlerOrderType {
    /**
     * 订单（策略）类型
     */
    OrderType value();
}
