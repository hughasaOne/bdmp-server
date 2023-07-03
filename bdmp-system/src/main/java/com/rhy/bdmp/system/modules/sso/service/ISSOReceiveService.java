package com.rhy.bdmp.system.modules.sso.service;

import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/5/12
 * @description: 单点登录请求接收端服务层接口
 * @version: 1.0
 */
public interface ISSOReceiveService {

    /**
     * 根据协同门户传递过来的token返回首页地址
     * @param appToken
     * @return
     */
    public Object getHomeUrl(String appToken);
}

