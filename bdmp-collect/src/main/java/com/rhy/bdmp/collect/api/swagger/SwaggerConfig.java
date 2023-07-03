package com.rhy.bdmp.collect.api.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket collectJdxt() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("机电系统数据采集-API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.collect.modules.jdxt"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket collectCamera() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("摄像头采集")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.collect.modules.camera"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket vp() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("可视对讲采集")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.collect.modules.vp"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

//    @Bean
//    public Docket rhyAutoCode() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("java代码自动生成API")
//                .apiInfo(apiInfo())
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
//                .select()
//                //包下的类，才生成接口文档
//                .apis(RequestHandlerSelectors.basePackage("com.rhy.bcp.generator"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基础数据平台-数据采集")
                .description("基础数据平台-数据采集v1.0.0后台相关接口")
                .termsOfServiceUrl("http://www.rhytech.com.cn/")
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
//        apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
        apiKeyList.add(new ApiKey("token", "token", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}