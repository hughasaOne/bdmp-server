package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 紧急电话 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDeviceEmergencycallService extends IService<DeviceEmergencycall> {

    /**
     * 紧急电话列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceEmergencycall> list(QueryVO queryVO);

    /**
     * 紧急电话列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceEmergencycall> page(QueryVO queryVO);

    /**
     * 查看紧急电话(根据ID)
     * @param deviceId
     * @return
     */
    DeviceEmergencycall detail(String deviceId);

    /**
     * 新增紧急电话
     * @param deviceEmergencycall
     * @return
     */
    int create(DeviceEmergencycall deviceEmergencycall);

    /**
     * 修改紧急电话
     * @param deviceEmergencycall
     * @return
     */
    int update(DeviceEmergencycall deviceEmergencycall);

    /**
     * 删除紧急电话
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
