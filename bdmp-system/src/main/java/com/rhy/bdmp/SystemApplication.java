package com.rhy.bdmp;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.rhy.bcp.common.exception.annotation.EnableException;
import com.rhy.bcp.common.util.SpringContextHolder;
import com.rhy.bdmp.system.api.swagger.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import com.rhy.bcp.common.redis.RedisConfig;

/**
 * @author: jiangzhimin
 * @Date: 2021/04/12
 * @description:  系统管理模块入口
 * @version: 1.0
 */
/*@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.rhy.bcp.logging", "com.rhy.bdmp.base", "com.rhy.bdmp.system.modules"})
@MapperScan(basePackages = {"com.rhy.bcp.logging.dao", "com.rhy.bdmp.base.modules.*.dao", "com.rhy.bdmp.system.modules.*.dao"})
@EnableException
@EnableSwagger
@Import({SpringContextHolder.class})*/

@SpringBootApplication
@MapperScan(basePackages = {"com.rhy.bdmp.open.modules.**.dao,com.jtkj.cloud.**.mapper"})
@EnableFeignClients(basePackages = {"com.jtkj.cloud.common.feign"})
@EnableDiscoveryClient
@EnableSwagger
@ComponentScan(basePackages = {"com.jtkj.cloud.**"})
@EnableException
//@Import({RedisConfig.class})
public class SystemApplication {

    public static void  main(String[] args){
        SpringApplication.run(SystemApplication.class, args);
    }

    /*
     * 分页插件
     */
    /*@Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

}
