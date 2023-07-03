package com.rhy.bdmp.open.modules.common.service;

import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.open.modules.common.dao.CommonDao;
import com.rhy.bdmp.open.modules.common.enums.NodeTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonService {

    @Resource
    private CommonDao commonDao;

    public void checkParams(String nodeId, String nodeType) {
        if ((StrUtil.isBlank(nodeId) != StrUtil.isBlank(nodeType))) {
            throw new BadRequestException("orgId 和 nodeType 必须同时为空或同时非空");
        }
        // 判断 nodeType 是否合法
        if (StrUtil.isNotBlank(nodeType) && !NodeTypeEnum.include(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + " 为非法参数,请检查");
        }
    }

    public UserPermissions getUserPermissions(Boolean isUseUserPermissions) {
        if (null == isUseUserPermissions){
            isUseUserPermissions = true;
        }
        UserPermissions userPermissions = new UserPermissions();
        // 带用户权限
        if (isUseUserPermissions) {
            User user = getCurrentUser();
            userPermissions.setDataPermissionsLevel(user.getDataPermissionsLevel());
            userPermissions.setUserId(user.getUserId());
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                // admin用户则不考虑权限
                isUseUserPermissions = false;
            } else if (!PermissionsEnum.include(String.valueOf(userPermissions.getDataPermissionsLevel()))) {
                // 非admin用户,数据权限为null时不通过
                throw new BadRequestException("您没有浏览该数据的权限");
            }
        }
        userPermissions.setIsUseUserPermissions(isUseUserPermissions);
        return userPermissions;
    }

    public String convertNodeType(String nodeType) {
        return NodeTypeEnum.getName(nodeType);
    }

    public User getCurrentUser() {
        String userId = WebUtils.getUserId();
        User user = commonDao.getUserById(userId);
        if (null == user) {
            throw new BadRequestException("用户信息不存在");
        }
        return user;
    }
}
