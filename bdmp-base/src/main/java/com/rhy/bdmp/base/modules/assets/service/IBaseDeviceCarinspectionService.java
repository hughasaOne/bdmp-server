package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 车检器 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDeviceCarinspectionService extends IService<DeviceCarinspection> {

    /**
     * 车检器列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceCarinspection> list(QueryVO queryVO);

    /**
     * 车检器列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceCarinspection> page(QueryVO queryVO);

    /**
     * 查看车检器(根据ID)
     * @param deviceId
     * @return
     */
    DeviceCarinspection detail(String deviceId);

    /**
     * 新增车检器
     * @param deviceCarinspection
     * @return
     */
    int create(DeviceCarinspection deviceCarinspection);

    /**
     * 修改车检器
     * @param deviceCarinspection
     * @return
     */
    int update(DeviceCarinspection deviceCarinspection);

    /**
     * 删除车检器
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
