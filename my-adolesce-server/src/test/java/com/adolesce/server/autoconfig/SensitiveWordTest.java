package com.adolesce.server.autoconfig;

import com.adolesce.common.utils.SensitiveWordUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/9/27 23:15
 */
public class SensitiveWordTest {

    @Test
    public void sensitiveScanTest() {
        //获取所有的敏感词
        List<String> sensitiveList = new ArrayList<>();
        sensitiveList.add("私人侦探");
        sensitiveList.add("冰毒");

        //初始化敏感词库
        SensitiveWordUtil.initMap(sensitiveList);

        //查看内容中是否包含敏感词
        String content = "郑重声明：本机构只提供私人侦探业务、但不提供冰毒业务，珍爱生命！远离冰毒！";
        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if (map.size() > 0) {
            System.out.println(map);
        }
    }
}
