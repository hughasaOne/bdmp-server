package com.rhy.bdmp.quartz;

import com.rhy.bcp.common.datasource.annotation.EnableMultipleDataSource;
import com.rhy.bcp.common.exception.annotation.EnableException;
import com.rhy.bcp.common.util.SpringContextHolder;
import com.rhy.bdmp.quartz.api.swagger.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author shuaichao
 * @create 2022-03-11 9:52
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.rhy.bdmp.base", "com.rhy.bdmp.quartz.**","com.jtkj.cloud.**"})
@MapperScan(basePackages = {"com.rhy.bdmp.base.modules.*.dao", "com.rhy.bdmp.quartz.modules.*.dao","com.jtkj.cloud.**.mapper"})
@EnableException
@EnableSwagger
@EnableScheduling
@EnableMultipleDataSource
/*@Import({SpringContextHolder.class})*/
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class,args);
    }
}
