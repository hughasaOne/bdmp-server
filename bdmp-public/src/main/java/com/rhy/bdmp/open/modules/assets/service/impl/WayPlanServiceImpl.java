package com.rhy.bdmp.open.modules.assets.service.impl;

import com.rhy.bdmp.open.modules.assets.dao.WayPlanDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanBO;
import com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanDetailBO;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.open.modules.assets.service.IWayPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2021/11/12.
 *
 * @author duke
 */
@Service
public class WayPlanServiceImpl implements IWayPlanService {

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;
    @Resource
    private WayPlanDao wayPlanDao;

    @Override
    public WayPlanBO queryWayPlan(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        if((dataPermissionsLevel == null && !isUseUserPermissions) || dataPermissionsLevel == 1){
            return wayPlanDao.getPlanMileage(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
        }
        return null;
    }

    @Override
    public List<WayPlanDetailBO> queryWayPlanDetail(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        if((dataPermissionsLevel == null && !isUseUserPermissions) || dataPermissionsLevel == 1){
            return wayPlanDao.getPlanMileageDetail(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
        }
        return null;
    }
}
