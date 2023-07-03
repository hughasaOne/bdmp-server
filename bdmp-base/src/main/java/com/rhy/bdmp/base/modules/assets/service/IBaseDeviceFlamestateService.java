package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 感温器 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDeviceFlamestateService extends IService<DeviceFlamestate> {

    /**
     * 感温器列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DeviceFlamestate> list(QueryVO queryVO);

    /**
     * 感温器列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DeviceFlamestate> page(QueryVO queryVO);

    /**
     * 查看感温器(根据ID)
     * @param deviceId
     * @return
     */
    DeviceFlamestate detail(String deviceId);

    /**
     * 新增感温器
     * @param deviceFlamestate
     * @return
     */
    int create(DeviceFlamestate deviceFlamestate);

    /**
     * 修改感温器
     * @param deviceFlamestate
     * @return
     */
    int update(DeviceFlamestate deviceFlamestate);

    /**
     * 删除感温器
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);


}
