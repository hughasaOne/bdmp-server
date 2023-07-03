package com.rhy.bdmp.open.modules.assets.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.dao.WayDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.bo.WayListBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.WaySectionShort;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.open.modules.assets.service.IWayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 路产业务处理类(路网, 运营路段, 路线, 路段...)
 * @Date: 2021/9/28 9:03
 * @Version: 1.0.0
 */
@Service
public class WayServiceImpl implements IWayService {

    @Resource
    private WayDao publicWayDao;

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    @Override
    public Map<String, Object> getTotalMileage(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        // 获取总里程数
        return publicWayDao.getTotalMileage(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    /**
     * 获取路段里程列表
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return
     */
//    @Override
//    public List<MileageBo> queryMileageListByWay(boolean isUseUserPermissions, String orgId, String nodeType){
//        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
//        String userId = userPermissions.getUserId();
//        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
//        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
//        // 获取路段里程列表
//        return publicWayDao.queryMileageListByWay(isUseUserPermissions, userId, dataPermissionsLevel, null);
//    }

    /**
     * 2.80.9 查询路段列表
     */
    @Override
    public Page<Waysection> getWayList(WayListBo wayListBo) {
        // 请求参数
        String orgId = wayListBo.getOrgId();
        String nodeType = wayListBo.getNodeType();
        Integer currentPage = wayListBo.getCurrentPage();
        Integer pageSize = wayListBo.getPageSize();
        Boolean isUseUserPermissions = wayListBo.getIsUseUserPermissions();

        // 用户信息
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        // 分页信息
        Page<Waysection> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);
        page.setOptimizeCountSql(false);
        return publicWayDao.getWayList(page,isUseUserPermissions,userId,dataPermissionsLevel,orgId,nodeType);
    }

    /**
     * 2.80.10 查询路段详情
     * @param wayId 路段id
     */
    @Override
    public Waysection getWayById(String wayId) {
        return publicWayDao.getWayById(wayId);
    }

    @Override
    public WaySectionShort getWaySectionShort(String orgId) {
        return publicWayDao.getWaySectionShort(orgId);
    }
}
