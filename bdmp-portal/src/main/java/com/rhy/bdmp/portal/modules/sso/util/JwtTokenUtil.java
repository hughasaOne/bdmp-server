package com.rhy.bdmp.portal.modules.sso.util;

import cn.hutool.json.JSONUtil;
import com.rhy.bdmp.portal.modules.sso.domain.UserAppMappingVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: lipeng
 * @Date: 2021/1/24
 * @description: JWT工具类
 * @version: 1.0
 */
@Slf4j
public class JwtTokenUtil {
    // Token请求头
    public static final String TOKEN_HEADER = "Authorization";
    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    // 过期时间
    public static final long EXPIRITION = 1000 * 24 * 60 * 60;
    // 应用密钥
    public static final String APPSECRET_KEY = "U2FsdGVkX19DceXQb6zPxia2LZzeVjOH";
    // 用户映射声明
    private static final String ROLE_MAPPING = "userMapping";

    /**
     * 生成Token
     */
    public static String createToken(UserAppMappingVO userAppMappingVO) {
        Map<String,Object> map = new HashMap<>();
        map.put("user",JSONUtil.toJsonStr(userAppMappingVO));

        String token = Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("portal")
                .setClaims(map)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }

    /**
     * 校验Token
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从Token中获取用户映射
     */
    public static UserAppMappingVO getUserAppMapping(String token){
        UserAppMappingVO userAppMappingVO = null;
        Claims claims = checkJWT(token);
        if(claims!= null){
            boolean isexp = claims.getExpiration().before(new Date());
            if(!isexp){
                String tokenUser = claims.get("user").toString();
                log.info("---appUserString---：{}", tokenUser);
                userAppMappingVO = JSONUtil.toBean(tokenUser, UserAppMappingVO.class);
            }
        }
       return userAppMappingVO;
    }

    /**
     * 校验Token是否过期
     */
    public static boolean isExpiration(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}