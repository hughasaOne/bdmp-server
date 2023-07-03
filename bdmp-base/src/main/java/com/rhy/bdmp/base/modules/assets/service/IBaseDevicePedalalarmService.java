package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DevicePedalalarm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 脚踏报警器 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDevicePedalalarmService extends IService<DevicePedalalarm> {

    /**
     * 脚踏报警器列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DevicePedalalarm> list(QueryVO queryVO);

    /**
     * 脚踏报警器列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DevicePedalalarm> page(QueryVO queryVO);

    /**
     * 查看脚踏报警器(根据ID)
     * @param deviceId
     * @return
     */
    DevicePedalalarm detail(String deviceId);

    /**
     * 新增脚踏报警器
     * @param devicePedalalarm
     * @return
     */
    int create(DevicePedalalarm devicePedalalarm);

    /**
     * 修改脚踏报警器
     * @param devicePedalalarm
     * @return
     */
    int update(DevicePedalalarm devicePedalalarm);

    /**
     * 删除脚踏报警器
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
