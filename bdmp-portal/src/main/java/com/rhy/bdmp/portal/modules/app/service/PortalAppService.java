package com.rhy.bdmp.portal.modules.app.service;

import com.rhy.bdmp.base.modules.sys.domain.po.App;

import java.util.List;

public interface PortalAppService {

    /**
    * @Description: 查询当前用户的应用访问权限
    * @Author: dongyu
    * @Date: 2021/5/11
    */
    List<App> findAppByCurrentUser(Boolean isUseUserPermissions);
}
