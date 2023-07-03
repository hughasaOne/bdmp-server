package com.rhy.bdmp.portal.modules.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.base.modules.sys.dao.BaseUserDao;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.portal.modules.app.dao.PortalAppDao;
import com.rhy.bdmp.portal.modules.app.service.PortalAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 应用查询
 * @Date: 2021/5/11
 */
@Service
@Slf4j
public class PortalAppServiceImpl implements PortalAppService {

    @Resource
    private PortalAppDao portalAppDao;

    @Resource
    private BaseUserDao userDao;

    /**
     * @Description: 查询当前用户的应用访问权限
     * @Author: dongyu
     * @Date: 2021/5/11
     */
    @Override
    @DataSource("baseData")
    public List<App> findAppByCurrentUser(Boolean isUseUserPermissions) {
        List<App> result = new ArrayList<>();
        //查询当前用户Id
        String userId = WebUtils.getUserId();
        if (StrUtil.isBlank(userId)) {
//            userId = "1";
            throw new BadRequestException("未获取到当前用户");
        }
        // 获取当前用户信息
        User user = userDao.selectById(userId);
        //判断用户是否为admin
        if (null != user.getIsAdmin() && user.getIsAdmin() == 1) {
            isUseUserPermissions = false;
        }
        //isUseUserPermissions等于true时按权限查找,isUseUserPermissions等于false查所有
        result = portalAppDao.findAppByUser(isUseUserPermissions, userId);
        return result;
    }
}
