package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 情报板 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDeviceIntelligenceboardService extends IService<DeviceIntelligenceboard> {

    /**
     * 情报板列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceIntelligenceboard> list(QueryVO queryVO);

    /**
     * 情报板列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceIntelligenceboard> page(QueryVO queryVO);

    /**
     * 查看情报板(根据ID)
     * @param deviceId
     * @return
     */
    DeviceIntelligenceboard detail(String deviceId);

    /**
     * 新增情报板
     * @param deviceIntelligenceboard
     * @return
     */
    int create(DeviceIntelligenceboard deviceIntelligenceboard);

    /**
     * 修改情报板
     * @param deviceIntelligenceboard
     * @return
     */
    int update(DeviceIntelligenceboard deviceIntelligenceboard);

    /**
     * 删除情报板
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
