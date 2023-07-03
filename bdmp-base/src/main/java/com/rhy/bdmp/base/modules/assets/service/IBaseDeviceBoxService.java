package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceBox;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 终端箱基本信息表 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDeviceBoxService extends IService<DeviceBox> {

    /**
     * 终端箱基本信息表列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceBox> list(QueryVO queryVO);

    /**
     * 终端箱基本信息表列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceBox> page(QueryVO queryVO);

    /**
     * 查看终端箱基本信息表(根据ID)
     * @param deviceId
     * @return
     */
    DeviceBox detail(String deviceId);

    /**
     * 新增终端箱基本信息表
     * @param deviceBox
     * @return
     */
    int create(DeviceBox deviceBox);

    /**
     * 修改终端箱基本信息表
     * @param deviceBox
     * @return
     */
    int update(DeviceBox deviceBox);

    /**
     * 删除终端箱基本信息表
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
