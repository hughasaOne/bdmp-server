package com.rhy.bdmp.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: lipeng
 * @Date: 2021/1/4
 * @description: 白名单配置
 * 获取系统白名单配置，如果是白名单则直接放行
 * @version: 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "whitelist")
public class WhiteListConfig {

    private List<String> urls;

}
