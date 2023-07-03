package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 设备 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDeviceService extends IService<Device> {

    /**
     * 设备列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Device> list(QueryVO queryVO);

    /**
     * 设备列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Device> page(QueryVO queryVO);

    /**
     * 查看设备(根据ID)
     * @param deviceId
     * @return
     */
    Device detail(String deviceId);

    /**
     * 新增设备
     * @param device
     * @return
     */
    int create(Device device);

    /**
     * 修改设备
     * @param device
     * @return
     */
    int update(Device device);

    /**
     * 删除设备
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
