package com.adolesce.nacos.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/9/30 16:14
 */
@RestController
@RequestMapping("/remoteCall")
public class CallStyleProviderController {
    @GetMapping("/sytle1/{id}")
    public Map findOne(@PathVariable("id") String id, @PathVariable("name") String name){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("name",name);
        return resultMap;
    }
}
