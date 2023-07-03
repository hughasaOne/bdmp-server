package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry;

import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
public interface IFacilitiesGantryService {

    /**
     * 新增门架
     * @param gantry
     * @return
     */
    int create(FacilitiesGantry gantry);

    /**
     * 删除门架
     * @param gantryIds
     * @return
     */
    int delete(Set<String> gantryIds);

    /**
     * 删除所有关联设施的数据
     * @param facilitiesIds
     * @return
     */
    int batchDelete(Set<String> facilitiesIds);
}
