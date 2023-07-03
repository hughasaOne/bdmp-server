package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;

import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
public interface IFacilitiesServiceAreaService {

    /**
     * 新增服务区
     * @param serviceArea
     * @return
     */
    int create(FacilitiesServiceArea serviceArea);

    /**
     * 删除服务区
     * @param serviceAreaIds
     * @return
     */
    int delete(Set<String> serviceAreaIds);

    /**
     * 删除所有关联设施的数据
     * @param facilitiesIds
     * @return
     */
    int batchDelete(Set<String> facilitiesIds);
}
