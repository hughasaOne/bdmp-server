package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;

import java.util.List;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
public interface IFacilitiesBridgeService {

    /**
     * 新增桥梁
     * @param bridge
     * @return
     */
    int create(FacilitiesBridge bridge);


    /**
     * 删除桥梁
     * @param bridgeIds
     * @return
     */
    int delete(Set<String> bridgeIds);

    /**
     * 删除所有关联设施的数据
     * @param facilitiesIds
     * @return
     */
    int batchDelete(Set<String> facilitiesIds);
}
