package com.rhy.bdmp.open;

import com.rhy.bcp.common.datasource.annotation.EnableMultipleDataSource;
import com.rhy.bcp.common.exception.annotation.EnableException;
import com.rhy.bcp.common.util.SpringContextHolder;
import com.rhy.bdmp.open.modules.api.swagger.EnableSwagger;
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


/**
 * @author: lipeng
 * @Date: 2021/7/10
 * @description: 基础数据管理平台对外公共服务入口
 * @version: 1.0.0
 */
/*@SpringBootApplication
@MapperScan(basePackages = {"com.rhy.bdmp.open.modules.*.dao"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger
//@EnableCorss
@EnableException
@EnableMultipleDataSource*/
@SpringBootApplication
@MapperScan(basePackages = {"com.rhy.bdmp.open.modules.**.dao,com.jtkj.cloud.**.mapper"})
@EnableFeignClients(basePackages = {"com.jtkj.cloud.common.feign","com.rhy.bdmp.open.modules.user.feignclient"
        ,"com.rhy.bdmp.open.modules.assets.feignclient","com.rhy.bdmp.open.modules.auth.feignclient"})
@EnableDiscoveryClient
@EnableSwagger
@ComponentScan(basePackages = {"com.jtkj.cloud.**","com.rhy.bdmp.open.**"})
@EnableException
/*@Import({SpringContextHolder.class})*/
public class BdmpPublicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BdmpPublicApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
