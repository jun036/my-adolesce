package com.adolesce.server.controller;

import com.adolesce.server.service.impl.DesignPatternService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 */
@RestController
@RequestMapping("designPattern")
public class DesignPatternController implements ApplicationContextAware {
    @Autowired
    private DesignPatternService designPatternService;

    /**
     * 使用【策略模式 + 自定义注解】消除繁琐的if else或swith判断
     */
    @RequestMapping("strategyMode1/{onOff}/{orderType}")
    public void strategyMode1(@PathVariable("onOff") String onOff,@PathVariable("orderType") int orderType){
        designPatternService.strategyMode1(onOff,orderType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
    }
}
