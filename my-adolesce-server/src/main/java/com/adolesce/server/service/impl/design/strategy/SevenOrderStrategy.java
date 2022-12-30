package com.adolesce.server.service.impl.design.strategy;

import com.adolesce.common.annotation.HandlerOrderType;
import com.adolesce.server.service.design.strategy.OrderStrategy;
import org.springframework.stereotype.Service;

import static com.adolesce.common.enums.OrderType.SEVEN_DISCOUNT;

@Service
@HandlerOrderType(SEVEN_DISCOUNT)
public class SevenOrderStrategy implements OrderStrategy {
    @Override
    public void handlerOrder() {
        System.out.println("----处理七折订单----");
    }
}