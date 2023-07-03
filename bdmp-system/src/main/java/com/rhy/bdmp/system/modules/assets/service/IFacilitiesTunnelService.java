package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;

import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
public interface IFacilitiesTunnelService {

    /**
     * 新增隧道
     * @param tunnel
     * @return
     */
    int create(FacilitiesTunnel tunnel);

    /**
     * 删除隧道
     * @param tunnelIds
     * @return
     */
    int delete(Set<String> tunnelIds);

    /**
     * 删除所有关联设施的数据
     * @param facilitiesIds
     * @return
     */
    int batchDelete(Set<String> facilitiesIds);
}
