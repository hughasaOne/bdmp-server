package com.rhy.bdmp.gateway;

import com.rhy.bdmp.gateway.modules.service.IGatewayRouteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author: lipeng
 * @Date: 2021/1/4
 * @description: 网关服务入口
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Resource
    private IGatewayRouteService configService;
    @Bean
    public void autoRegisterRoute(){
        configService.routeRegister("app");
    }
}
