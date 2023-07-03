package com.rhy.bdmp.open.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.domain.bo.WayListBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.WaySectionShort;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;

import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 路段业务
 * @Date: 2021/9/28 9:03
 * @Version: 1.0.0
 */
public interface IWayService {
    /**
     * 获取告诉路总里程
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return String
     */
    Map<String, Object> getTotalMileage(boolean isUseUserPermissions, String orgId, String nodeType);


    /**
     * 获取运营公司的高速路总里程
     *
     * @param orgId 运营公司id
     * @return Double
     */
    WaySectionShort getWaySectionShort(String orgId);

    /**
     * 获取路段里程列表
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return
     */
//    List<MileageBo> queryMileageListByWay(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 2.80.9 查询路段列表
     */
    Page<Waysection> getWayList(WayListBo wayListBo);

    /**
     * 2.80.10 查询路段详情
     * @param wayId 路段id
     */
    Waysection getWayById(String wayId);
}
