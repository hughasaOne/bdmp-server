package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;

import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
public interface IFacilitiesTollStationService {

    /**
     * 新增收费站
     * @param tollStation
     * @return
     */
    int create(FacilitiesTollStation tollStation);

    /**
     * 删除收费站
     * @param tollStationIds
     * @return
     */
    int delete(Set<String> tollStationIds);

    /**
     * 删除所有关联设施的数据
     * @param facilitiesIds
     * @return
     */
    int batchDelete(Set<String> facilitiesIds);
}
