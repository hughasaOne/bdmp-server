package com.rhy.bdmp.open.modules.assets.service.impl;

import com.rhy.bdmp.open.modules.assets.dao.AppDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.AppDirBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.vo.AppDirVo;
import com.rhy.bdmp.open.modules.assets.service.IAppService;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 应用服务
 * @author weicaifu
 */
@Service
public class AppService implements IAppService {

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsService;

    @Resource
    private AppDao appDao;

    /**
     * 获取应用目录
     * @param appDirBo 业务类
     */
    @Override
    public List<AppDirVo> getAppDir(AppDirBo appDirBo) {
        UserPermissions userPermissions = assetsPermissionsService.getUserPermissions(appDirBo.getIsUseUserPermissions());
        return appDao.getAppDir(appDirBo,userPermissions);
    }
}
