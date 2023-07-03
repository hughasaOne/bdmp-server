package com.rhy.bdmp.auth.config;


import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class AuthenticationFailureListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${locale.login.loginFailureKey}")
    private String loginFailureKey;

    @Value("${locale.login.loginFailureTimeMinutesLimit}")
    private int loginFailureTimeMinutesLimit;

    @EventListener
    public void onLoginSuccess(AuthenticationSuccessEvent loginSuccess) {
        // details不为null，说明是用户账号认证，否则：客户端认证
        if (!Objects.isNull(JSONUtil.parseObj(loginSuccess.getSource()).get("details"))){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String username = request.getParameter("username");
            String redisKey = loginFailureKey + username;
            redisTemplate.delete(redisKey);
        }
    }

    @EventListener
    public void onLoginFailure(AuthenticationFailureBadCredentialsEvent loginFailure) {
        // details不为null，说明是用户账号认证，否则：客户端认证
        if (!Objects.isNull(JSONUtil.parseObj(loginFailure.getSource()).get("details"))){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String username = request.getParameter("username");
            String redisKey = loginFailureKey + username;
            Object o = redisTemplate.opsForValue().get(redisKey);
            int loginFailureNum = (null == o) ? 0 : (int) o;
            loginFailureNum++;
            redisTemplate.opsForValue().set(redisKey, loginFailureNum, loginFailureTimeMinutesLimit, TimeUnit.MINUTES);
        }
    }

}
