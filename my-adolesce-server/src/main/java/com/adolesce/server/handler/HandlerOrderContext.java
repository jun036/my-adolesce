package com.adolesce.server.handler;

import com.adolesce.common.annotation.HandlerOrderType;
import com.adolesce.common.enums.OrderType;
import com.adolesce.server.service.design.strategy.OrderStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     自定义spring处理器。把所有的策略类存储到Map集合中。
 * </p>
 *
 * @author: heluwei
 * @Date: 2020/4/22 17:58
 */
@Component
public class HandlerOrderContext implements ApplicationContextAware {
    private Map<Integer,OrderStrategy> orderStrategyBeanMap = new HashMap<>();

    /**
     * 获取所有的策略Bean 加入HandlerOrderContext属性中
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有策略注解的Bean
        Map<String, Object> orderStrategyMap = applicationContext.getBeansWithAnnotation(HandlerOrderType.class);
        //Map<String, OrderStrategy> orderStrategyMap2= applicationContext.getBeansOfType(OrderStrategy.class);
        orderStrategyMap.values().forEach( v ->{
            //获取策略实现类
            Class<OrderStrategy> orderStrategyClass = (Class<OrderStrategy>) v.getClass();
            //获取策略实现类的注解值。
            OrderType orderType = orderStrategyClass.getAnnotation(HandlerOrderType.class).value();
            //将class加入HandlerOrderContext的map中,type作为key
            orderStrategyBeanMap.put(orderType.key,(OrderStrategy)v);
        });
    }

    public OrderStrategy getOrderStrategy(Integer type){
        //从Map中获取对应的策略Bean
        OrderStrategy strategy = orderStrategyBeanMap.get(type);
        if(strategy==null){
            throw new IllegalArgumentException("没有对应的订单类型");
        }
        return strategy;
    }
}