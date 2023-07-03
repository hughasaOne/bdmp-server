package com.rhy.bdmp.base.modules.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceExternal;

import java.util.List;
import java.util.Set;

/**
 * @description 外接设备 服务接口
 * @author weicaifu
 * @date 2022-10-28 16:43
 * @version V1.0
 **/
public interface IBaseDeviceExternalService extends IService<DeviceExternal> {

    /**
     * 外接设备列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceExternal> list(QueryVO queryVO);

    /**
     * 外接设备列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceExternal> page(QueryVO queryVO);

    /**
     * 查看外接设备(根据ID)
     * @param id
     * @return
     */
    DeviceExternal detail(String id);

    /**
     * 新增外接设备
     * @param deviceExternal
     * @return
     */
    int create(DeviceExternal deviceExternal);

    /**
     * 修改外接设备
     * @param deviceExternal
     * @return
     */
    int update(DeviceExternal deviceExternal);

    /**
     * 删除外接设备
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
