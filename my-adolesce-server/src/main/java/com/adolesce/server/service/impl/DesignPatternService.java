package com.adolesce.server.service.impl;

import com.adolesce.server.handler.HandlerOrderContext;
import com.adolesce.server.service.design.strategy.OrderStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/10/10 6:51
 */
@Service
public class DesignPatternService {
    @Autowired
    private HandlerOrderContext handlerOrderContext;

    /**
     *  使用【策略模式 + 自定义注解】消除繁琐的if else或swith判断
     * @param onOff 启用策略模式 开关 【open、close】
     * @param orderType 业务订单类型
     */
    public void strategyMode1(String onOff, int orderType) {
        if(StringUtils.equals("close",onOff)){
            switch (orderType){
                case 1: {
                    System.out.println("11111");
                    break;
                }
                case 2:{
                    System.out.println("222222");
                    break;
                }
                case 3:{
                    System.out.println("33333333");
                    break;
                }
                case 4:{
                    System.out.println("44444444444");
                    break;
                }
                default:
                    break;
            }
        }else{
            OrderStrategy orderStrategy = handlerOrderContext.getOrderStrategy(orderType);
            orderStrategy.handlerOrder();
        }
    }
}
