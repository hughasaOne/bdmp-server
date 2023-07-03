package com.rhy.bdmp.system.modules.sso.service.impl;

import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sso.dao.SSOReceiveDao;
import com.rhy.bdmp.system.modules.sso.service.ISSOReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/5/12
 * @description: 单点登录请求接收端服务层实现
 * @version: 1.0
 */
@Service
@Slf4j
public class SSOReceiveServiceImpl implements ISSOReceiveService {

    @Resource
    private SSOReceiveDao ssoReceiveDao;

    @Override
    public Object getHomeUrl(String appToken) {
        /**
         * 由于基础数据管理平台会接入网关，由网关进行统一认证，不需要重新创建token,直接将token带入到首页地址中即可
         */
        if(StrUtil.isBlank(appToken)){
            throw new BadRequestException("协同门户单点登录token为空！");
        }
        //从系统参数表中获取当前系统的首页地址
        String homeUrl = ssoReceiveDao.queryHoneUrl();
        homeUrl =  homeUrl + "?token="+appToken;
        Map dataMap = new HashMap();
        dataMap.put("token",appToken);
        dataMap.put("appUrl", homeUrl);
        log.info(LogUtil.buildUpParams("协同门户获取首页地址", LogTypeEnum.GET_RESOURCE.getCode(), homeUrl));
        return dataMap;
    }
}
