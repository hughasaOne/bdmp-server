package com.rhy.bdmp.collect.api.swagger;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * <p><b>启用Swagger</b></p>
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerConfig.class})
public @interface EnableSwagger {
}
