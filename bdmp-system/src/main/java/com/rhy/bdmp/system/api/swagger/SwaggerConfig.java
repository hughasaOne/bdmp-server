package com.rhy.bdmp.system.api.swagger;

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
    public Docket bdmpSys() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统资源API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.system.modules.sys"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket bdmpAssets() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("台账资源API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.system.modules.assets"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket bdmpLog() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统日志API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bcp.logging"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket rhyAutoCode() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("java代码自动生成API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bcp.generator"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket analysis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("统计分析API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.system.modules.analysis"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket bdmpSSO() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("单点登录API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.system.modules.sso"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket bdmpGateWayConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("配置API")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.system.modules.gateway"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

//    @Bean
//    public Docket bdmpLogging() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("操作日志API")
//                .apiInfo(apiInfo())
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
//                .select()
//                //包下的类，才生成接口文档
//                .apis(RequestHandlerSelectors.basePackage("com.rhy.bcp.logging"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基础数据管理平台")
                .description("基础数据管理平台后台相关接口")
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