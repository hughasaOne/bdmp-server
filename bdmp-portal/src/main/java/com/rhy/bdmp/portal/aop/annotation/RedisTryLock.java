package com.rhy.bdmp.portal.aop.annotation;

import java.lang.annotation.*;

/**
 * 功能描述 切面注解用于解决分布式定时任务重复执行
 * @author shuaichao
 * @create 2022-03-07 14:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisTryLock {
    /**
     * 锁的有效时长 单位：秒
     * @return
     */
    int expireTime() default 10;

    /**
     * 锁的KEY值
     * @return
     */
    String keyName() default  "";
}
