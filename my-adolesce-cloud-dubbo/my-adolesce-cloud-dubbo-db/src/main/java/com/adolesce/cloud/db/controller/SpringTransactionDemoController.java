package com.adolesce.cloud.db.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/4/26 12:29
 */
@RestController
@RequestMapping("transaction")
@Slf4j
public class SpringTransactionDemoController {
    @Autowired
    private SpringTransactionDemoService springTransactionDemoService;

    @PostMapping("addMpUser/{type}")
    public String addMpUser(@PathVariable("type") Integer type) {
        try {
            if (type == 1) {
                this.springTransactionDemoService.addMpUserToDB1();
            } else if (type == 2) {
                this.springTransactionDemoService.addMpUserToDB2();
            } else if (type == 3) {
                this.springTransactionDemoService.addMpUserToDB3();
            } else if (type == 4) {
                this.springTransactionDemoService.addMpUserToDB4();
            } else if (type == 5) {
                this.springTransactionDemoService.addMpUserToDB5();
            } else if (type == 6) {
                this.springTransactionDemoService.addMpUserToDB6();
            } else if (type == 7) {
                this.springTransactionDemoService.addMpUserToDB7();
            } else if (type == 8) {
                this.springTransactionDemoService.addMpUserToDB8();
            } else if (type == 9) {
                this.springTransactionDemoService.addMpUserToDB9();
            } else if (type == 10) {
                this.springTransactionDemoService.addMpUserToDB10();
            } else if (type == 11) {
                this.springTransactionDemoService.addMpUserToDB11();
            } else if (type == 12) {
                this.springTransactionDemoService.addMpUserToDB12();
            } else if (type == 13) {
                this.springTransactionDemoService.addMpUserToDB13();
            } else if (type == 14) {
                this.springTransactionDemoService.addMpUserToDB14();
            }
        } catch (Exception e) {
            log.error("保存用户信息出错：{}", e);
        }
        return "操作成功！";
    }

    /**
     * 保存用户测试方法
     *
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/addMpUserToDB88")
    public String addMpUserToDB88() {
        String addResult = this.springTransactionDemoService.add88MpUserToDB();
        return addResult;
    }

    /**
     * 保存用户测试方法
     *
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/addMpUserToDB99")
    public String addMpUserToDB99() {
        synchronized (this){
            String addResult = this.springTransactionDemoService.add88MpUserToDB();
            return addResult;
        }
    }

}
