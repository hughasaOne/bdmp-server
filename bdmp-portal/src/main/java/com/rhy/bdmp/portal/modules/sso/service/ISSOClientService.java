package com.rhy.bdmp.portal.modules.sso.service;


import com.rhy.bdmp.base.modules.sys.domain.po.App;

/**
 * @author: lipeng
 * @Date: 2021/5/11
 * @description: 业务系统单点登录服务层接口
 * @version: 1.0
 */
public interface ISSOClientService {

    /**
     * 根据应用ID获取应用跳转地址
     * @param appId
     * @return
     */
    public String getAppUrl(String appId);

    /**
     * 根据应用信息返回系统的用户映射生成token
     * @param app
     * @return
     */
    public String getAppToken(App app);
}
