package com.rhy.bdmp.portal;

import com.rhy.bcp.common.datasource.annotation.EnableMultipleDataSource;
import com.rhy.bcp.common.exception.annotation.EnableException;
import com.rhy.bcp.common.util.SpringContextHolder;
import com.rhy.bdmp.portal.api.swagger.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.rhy.bdmp.base", "com.rhy.bdmp.portal.modules","com.ovit.cloud.**"})
@MapperScan(basePackages = {"com.rhy.bdmp.base.modules.*.dao", "com.rhy.bdmp.portal.modules.*.dao","com.ovit.cloud.**.mapper"})
@EnableException
@EnableSwagger
@EnableScheduling
@Import({SpringContextHolder.class})
/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description: 协同门户服务入口
 * @version: 1.0.0
 */
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
