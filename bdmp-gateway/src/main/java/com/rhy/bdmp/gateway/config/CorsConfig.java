package com.rhy.bdmp.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
* @description 允许跨域配置
* @author jiangzhimin
* @date 2021-11-10 12:57
* @version V1.0
**/
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*"); // 允许所有请求方法，例如get，post等
        config.addAllowedOrigin("*"); // 允许所有的请求来源
        config.addAllowedHeader("*"); // 允许所有请求头
        config.setAllowCredentials(true); // 允许携带cookie
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config); // 对所有经过网关的请求都生效
        return new CorsWebFilter(source);
    }

}