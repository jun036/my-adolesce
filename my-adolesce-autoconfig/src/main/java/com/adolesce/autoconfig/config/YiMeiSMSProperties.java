package com.adolesce.autoconfig.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 亿美 短信发送 配置
 */
//@Component
//@PropertySource("classpath:sms.properties")
@Data
@ConfigurationProperties(prefix = "sms.yimei")
public class YiMeiSMSProperties {
    private String appId;
    private String secretKey;
    private String signName;
    private String singleUrl;
    private String batchUrl;
}