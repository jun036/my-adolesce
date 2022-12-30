package com.adolesce.server.service.impl.design.strategy;

import com.adolesce.common.annotation.HandlerOrderType;
import com.adolesce.server.service.design.strategy.OrderStrategy;
import org.springframework.stereotype.Service;

import static com.adolesce.common.enums.OrderType.FREE;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 */
@Service
@HandlerOrderType(FREE) //使用注解标明策略类型
public class FreeOrderStrategy implements OrderStrategy {

    @Override
    public void handlerOrder() {
        System.out.println("----处理免费订单----");
    }
}
