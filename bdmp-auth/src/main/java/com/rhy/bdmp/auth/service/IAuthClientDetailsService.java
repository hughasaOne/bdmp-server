package com.rhy.bdmp.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;

import java.util.List;

/**
 * @description oauth2认证配置 服务接口
 * @author shuaichao
 * @date 2022-03-23 11:18
 * @version V1.0
 **/
public interface IAuthClientDetailsService extends IService<AuthClientDetails> {




    /**
     * 新增oauth2认证配置
     * @param authClientDetails
     * @return
     */
    int create(AuthClientDetails authClientDetails);

    /**
     * 修改oauth2认证配置
     * @param authClientDetails
     * @return
     */
    int update(AuthClientDetails authClientDetails);


    List<AuthClientDetails> list(AuthClientDetails authClientDetails);

    PageUtil<AuthClientDetails> page(AuthClientDetails authClientDetails, Integer currentPage, Integer size);

    public  AuthClientDetails selectByClientId(String clientId);
}
