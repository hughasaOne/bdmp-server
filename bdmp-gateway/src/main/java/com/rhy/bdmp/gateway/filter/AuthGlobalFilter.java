package com.rhy.bdmp.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.rhy.bcp.common.constant.AuthConstants;
import com.rhy.bdmp.gateway.common.resutl.Result;
import com.rhy.bdmp.gateway.common.resutl.ResultCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @author: lipeng
 * @Date: 2021/1/4
 * @description: 全局过滤器
 * 当鉴权通过后将JWT令牌中的用户信息解析出来，然后存入请求的Header中，后续服务就不需要解析JWT令牌了，可以直接从请求的Header中获取到用户信息
 * @version: 1.0.0
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=======请求URL：" + exchange.getRequest().getURI().getPath());

        String token = exchange.getRequest().getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            //直接通过，传输到下一级
            return chain.filter(exchange);
        }

        //从token中解析用户信息并设置到Header中去
        token = token.replace(AuthConstants.JWT_TOKEN_PREFIX, Strings.EMPTY);
        JWSObject jwsObject = JWSObject.parse(token);
        String userStr = jwsObject.getPayload().toString();
        log.info("AuthGlobalFilter.filter() user:{}",userStr);

        // 黑名单token(登出、修改密码)校验
        JSONObject jsonObject = JSONUtil.parseObj(userStr);
        String jti = jsonObject.getStr("jti"); // JWT唯一标识

        Boolean isBlack = redisTemplate.hasKey(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlack) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.getHeaders().set("Access-Control-Allow-Origin", "*");
            response.getHeaders().set("Cache-Control", "no-cache");
            String body = JSONUtil.toJsonStr(Result.failed(ResultCode.TOKEN_INVALID_OR_EXPIRED));
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
            return response.writeWith(Mono.just(buffer));
        }
        if (StrUtil.isNotBlank(userStr)){
            userStr = URLEncoder.encode(userStr, "UTF-8");
        }
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(AuthConstants.JWT_PAYLOAD_KEY, userStr)
                .header(AuthConstants.JWT_TOKEN_SRR,token)
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
