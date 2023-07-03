package com.rhy.bdmp.auth.service.impl;

import lombok.SneakyThrows;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
/**
 * @author: lipeng
 * @Date: 2021/1/7
 * @description:  重写ClientDetailsService实现类JdbcClientDetailsService，自定义客户端数据查询
 * @version: 1.0.0
 */
public class JdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

    public JdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId)  {
        return super.loadClientByClientId(clientId);
    }
}
