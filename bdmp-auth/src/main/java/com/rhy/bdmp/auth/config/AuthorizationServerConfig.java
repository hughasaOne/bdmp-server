package com.rhy.bdmp.auth.config;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.constant.AuthConstants;
import com.rhy.bcp.common.resutl.Result;
import com.rhy.bcp.common.resutl.ResultCode;
import com.rhy.bdmp.auth.domain.vo.UserVo;
import com.rhy.bdmp.auth.filter.CustomClientCredentialsTokenEndpointFilter;
import com.rhy.bdmp.auth.service.impl.JdbcClientDetailsServiceImpl;
import com.rhy.bdmp.auth.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description: 认证服务器配置
 * 添加认证服务相关配置
 * @version: 1.0.0
 */
@Configuration
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private DataSource dataSource;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 配置客户端详情(数据库)
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        JdbcClientDetailsServiceImpl jdbcClientDetailsService = new JdbcClientDetailsServiceImpl(dataSource);
        jdbcClientDetailsService.setFindClientDetailsSql(AuthConstants.FIND_CLIENT_DETAILS_SQL);
        jdbcClientDetailsService.setSelectClientDetailsSql(AuthConstants.SELECT_CLIENT_DETAILS_SQL);
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer()); //配置JWT的内容增强器
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService) //配置加载用户信息的服务
                // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                // 1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //  非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                .reuseRefreshTokens(true);
    }

    /**
     * 端点安全配置
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        /*security.allowFormAuthenticationForClients();*/
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
        security.addTokenEndpointAuthenticationFilter(endpointFilter);

        security.authenticationEntryPoint(authenticationEntryPoint())
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    /**
     * 自定义认证异常响应数据
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            Result result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }


    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair(
                "jwt", "123456".toCharArray());
        return keyPair;
    }

    /**
     * JWT内容增强
     * 如果你想往JWT中添加自定义信息的话，比如说登录用户的ID，可以自己实现TokenEnhancer接口；
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            UserVo user = (UserVo) authentication.getUserAuthentication().getPrincipal();
            map.put(AuthConstants.JWT_USER_ID_KEY, user.getId());
            map.put(AuthConstants.JWT_CLIENT_ID_KEY, user.getClientId());
            map.put(AuthConstants.JWT_CLIENT_ORG_ID_KEY, user.getOrgId());
            map.put(AuthConstants.JWT_CLIENT_APP_ID_KEY,user.getAppId());
            map.put("operation_group_id",user.getOperationGroupId());
            map.put("operation_company_id",user.getOperationCompanyId());
            map.put("nickName",user.getNickName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}