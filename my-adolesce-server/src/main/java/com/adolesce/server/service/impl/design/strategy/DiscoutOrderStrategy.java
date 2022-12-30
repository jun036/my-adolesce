package com.adolesce.server.service.impl.design.strategy;

import com.adolesce.common.annotation.HandlerOrderType;
import com.adolesce.server.service.design.strategy.OrderStrategy;
import org.springframework.stereotype.Service;

import static com.adolesce.common.enums.OrderType.DISCOUT;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 */
@Service
@HandlerOrderType(DISCOUT) //使用注解标明策略类型
public class DiscoutOrderStrategy implements OrderStrategy {
    @Override
    public void handlerOrder() {
        System.out.println("----处理打折订单----");
    }
}
