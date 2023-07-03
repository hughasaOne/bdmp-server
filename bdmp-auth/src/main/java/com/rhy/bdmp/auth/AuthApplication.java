package com.rhy.bdmp.auth;

import com.rhy.bcp.common.exception.annotation.EnableException;
import com.rhy.bdmp.auth.config.swagger.EnableSwagger;
import com.rhy.bdmp.common.RedisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/*@EnableAuthorizationServer
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
//@EnableCorss
@EnableSwagger

@ComponentScan(basePackages = {"com.rhy.bdmp.base", "com.rhy.bdmp.auth"})
@MapperScan(basePackages = {"com.rhy.bdmp.base.modules.*.dao", "com.rhy.bdmp.auth.modules.*.dao" ,"com.rhy.bdmp.auth.dao"})
@Import({RedisConfig.class})*/

@SpringBootApplication
@MapperScan(basePackages = {"com.rhy.bdmp.base.modules.*.dao", "com.rhy.bdmp.auth.modules.*.dao" ,"com.rhy.bdmp.auth.dao","com.ovit.cloud.**.mapper"})
@EnableFeignClients(basePackages = {"com.ovit.cloud.common.feign"})
@EnableDiscoveryClient
@EnableSwagger
@ComponentScan(basePackages = {"com.ovit.cloud.**","com.rhy.bdmp.base", "com.rhy.bdmp.auth"})
@EnableException
/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description: 认证授权服务入口
 * @version: 1.0.0
 */
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
