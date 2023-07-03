package com.rhy.bdmp.open.modules.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * springboot 无法直接访问静态资源，需要放开资源访问路径。
     * 添加静态资源文件，外部可以直接访问地址
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //这里配置静态资源访问，即所有指向/static的请求都会去/static包下找
        registry.addResourceHandler("/bdmp/public/static/**").addResourceLocations(
                "classpath:/static/");
    }

}
