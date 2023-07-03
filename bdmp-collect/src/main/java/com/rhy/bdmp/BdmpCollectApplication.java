package com.rhy.bdmp;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.rhy.bcp.common.datasource.annotation.EnableMultipleDataSource;
import com.rhy.bcp.common.exception.annotation.EnableException;
import com.rhy.bcp.common.util.SpringContextHolder;
import com.rhy.bdmp.collect.api.swagger.EnableSwagger;
import com.rhy.bdmp.collect.modules.camera.service.VideoComprehensiveManageFeignClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author: jiangzhimin
 * @Date: 2021/08/02
 * @description:  采集系统入口
 * @version: 1.0
 */
/*@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.rhy.bdmp.base", "com.rhy.bdmp.collect.modules"})
@MapperScan(basePackages = {"com.rhy.bdmp.base.modules.*.dao", "com.rhy.bdmp.collect.modules.*.dao"})
@EnableException
@EnableSwagger
@EnableMultipleDataSource
@Import({SpringContextHolder.class})
@EnableAsync
@EnableScheduling
@EnableFeignClients(basePackageClasses = VideoComprehensiveManageFeignClient.class)*/

@SpringBootApplication
@MapperScan(basePackages = {"com.rhy.bdmp.open.modules.**.dao,com.jtkj.cloud.**.mapper"})
@EnableFeignClients(basePackages = {"com.jtkj.cloud.common.feign"})
@EnableDiscoveryClient
@EnableSwagger
@ComponentScan(basePackages = {"com.jtkj.cloud.**"})
@EnableException
public class BdmpCollectApplication {

    public static void  main(String[] args){
        SpringApplication.run(BdmpCollectApplication.class, args);
    }

    /*
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
