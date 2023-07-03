package com.rhy.bdmp.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description:  SpringSecurity 安全配置
 * @version: 1.0.0
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录失败处理handler，返回一段json
        http.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                .authorizeRequests().antMatchers("/oauth/token", "/oauth/logout","/oauth/loadUserByUsername","/doc.html","/webjars/**","/img.icons/**","/swagger-resources/**","/**","/v2/api-docs").permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable();

    }

    /**
     * 如果不配置SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
