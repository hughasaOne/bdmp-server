package com.rhy.bdmp.auth.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.rhy.bcp.common.constant.AuthConstants;
import com.rhy.bcp.common.resutl.Result;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.auth.service.UserService;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description:  认证中心控制器
 * @version: 1.0.0
 */
@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private KeyPair keyPair;
    @Value("${locale.login.loginFailureKey}")
    private String loginFailureKey;
    @Value("${locale.login.loginFailureNumLimit}")
    private int loginFailureNumLimit;
    @Value("${locale.login.loginFailureNumLimitMsg}")
    private String loginFailureNumLimitMsg;


    @ApiOperation("登录认证生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码")
    })
    @PostMapping("/token")
    public Result postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        log.info("=========================,{}", JSONUtil.toJsonStr(parameters));
//        String clientId = parameters.get("client_id");

//        if (StrUtil.isBlank(clientId)) {
//            throw new BizException("客户端ID不能为空");
//        }
        // 判断用户是否在指定时间(x分钟)内是否已连续失败n次
        String username = parameters.get("username");
        String redisKey = loginFailureKey + username;
        Object o = redisTemplate.opsForValue().get(redisKey);
        int loginFailureNum = null == o ? 0 : (int) o;
        if (loginFailureNum >= loginFailureNumLimit) {
            return Result.failed(loginFailureNumLimitMsg);
        }

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
//        Oauth2Token oauth2Token = Oauth2Token.builder()
//                .token(oAuth2AccessToken.getValue())
//                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
//                .expiresIn(oAuth2AccessToken.getExpiresIn())
//                .build();

//        return Result.success(oauth2Token);
        return Result.success(oAuth2AccessToken);
    }

    @ApiOperation("退出系统")
    @DeleteMapping("/logout")
    public Result logout() {
        JSONObject jsonObject = WebUtils.getJwtPayload();
        String jti = jsonObject.getStr("jti"); // JWT唯一标识
        long exp = jsonObject.getLong("exp"); // JWT过期时间戳

        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        if (exp < currentTimeSeconds) { // token已过期，无需加入黑名单
            return Result.success();
        }
        redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        //清除数据缓存
        redisTemplate.delete("aibox_sn_list_" + WebUtils.getUserId());
        return Result.success();
    }
    @ApiOperation("获取公钥")
    @GetMapping("/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }



    @PostMapping("/test")
    public Result test(
            @ApiIgnore @RequestParam Map<String, String> parameters) {
        return Result.success();
    }


    @Resource
    private UserService userService;

    @GetMapping("/loadUserByUsername")
    public Result loadUserByUsername(){
        User user= userService.getUserByUsername("lipeng");
        return Result.success(user);
    }

}
