package com.adolesce.server.controller;

import com.adolesce.server.properties.DemoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/5/19 13:15
 */
@RestController
@RequestMapping("/properties")
public class PropertiesDemoController {
    @Autowired
    private DemoProperties demoProperties;
    @Value("${demo.one}")
    private String one;
    @Value("${demo.two}")
    private String two;
    @Value("${demo.three}")
    private String three;

    @GetMapping("/printByConfigu")
    public String printByConfigu() {
        return demoProperties.toString();
    }

    @GetMapping("/printByValue")
    public String printByValue() {
        return "one:" + one + " two:" + two + " three:" + three;
    }
}
