package com.rhy.bdmp.open.modules.api.swagger;

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
    public Docket assetsBase() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("台账-基础数据服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.assets.controller.base"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket assetsScreen() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("台账-大屏数据服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.assets.controller.screen"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket system() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公共用户服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.system"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket bdmpAuth() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公共登录认证AP")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.auth"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket bdmpPts() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公众出行服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.pts"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket app() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("app服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.app"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket device() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("设备服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.device.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket org() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("组织机构服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.org.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket dict() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("字典服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.dict.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket fac() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("设施服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.fac.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket user() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.user.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket dir() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("资源目录服务")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.rhy.bdmp.open.modules.assets.controller.dir"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基础数据公共服务")
                .description("基础数据管理-公共服务接口")
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