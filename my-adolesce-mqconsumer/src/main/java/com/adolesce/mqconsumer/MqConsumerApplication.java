package com.adolesce.mqconsumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 */

@SpringBootApplication
@MapperScan("com.adolesce.common.mapper")
@ComponentScan(basePackages = "com.adolesce")
public class MqConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqConsumerApplication.class, args);
    }
}