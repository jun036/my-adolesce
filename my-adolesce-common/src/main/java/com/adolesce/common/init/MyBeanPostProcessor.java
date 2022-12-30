package com.adolesce.common.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/5/4 19:50
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {
    @Autowired
    private StringRedisTemplate redisTemplate; //have

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("通过MyBeanPostProcessor1前置初始化执行....");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("通过MyBeanPostProcessor1后置初始化执行....");
        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
