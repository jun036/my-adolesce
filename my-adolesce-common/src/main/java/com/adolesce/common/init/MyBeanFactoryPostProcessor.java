package com.adolesce.common.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Autowired
    private StringRedisTemplate redisTemplate;//nohave

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.err.println("2、通过BeanFactoryPostProcessor1初始化执行....");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
