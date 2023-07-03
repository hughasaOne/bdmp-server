package com.rhy.bdmp.system.modules.assets.permissions;

import com.rhy.bdmp.system.modules.assets.dao.AssetsPermissionsTreeDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class WayUserPermissions implements PermissionsAbstract {
    @Resource
    private AssetsPermissionsTreeDao assetsPermissionsTreeDao;

    @Override
    public Set<String> getPermissions(String userId) {
        return assetsPermissionsTreeDao.getPermissionsLevel2(userId);
    }
}
