package com.rhy.bdmp.base.modules.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;

import java.util.List;
import java.util.Set;

/**
 * @description 设备分类 服务接口
 * @author weicaifu
 * @date 2022-10-17 10:45
 * @version V1.0
 **/
public interface IBaseDeviceCategoryService extends IService<DeviceCategory> {

    /**
     * 设备分类列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceCategory> list(QueryVO queryVO);

    /**
     * 设备分类列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceCategory> page(QueryVO queryVO);

    /**
     * 查看设备分类(根据ID)
     * @param id
     * @return
     */
    DeviceCategory detail(String id);

    /**
     * 新增设备分类
     * @param deviceCategory
     * @return
     */
    int create(DeviceCategory deviceCategory);

    /**
     * 修改设备分类
     * @param deviceCategory
     * @return
     */
    int update(DeviceCategory deviceCategory);

    /**
     * 删除设备分类
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
