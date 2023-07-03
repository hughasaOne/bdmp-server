package com.rhy.bdmp.base.modules.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;

import java.util.List;
import java.util.Set;

/**
 * @description 设备字典 服务接口
 * @author yanggj
 * @date 2021-10-26 09:05
 * @version V1.0
 **/
public interface IBaseDictDeviceService extends IService<DictDevice> {

    /**
     * 设备字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DictDevice> list(QueryVO queryVO);

    /**
     * 设备字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DictDevice> page(QueryVO queryVO);

    /**
     * 查看设备字典(根据ID)
     * @param deviceDictId
     * @return
     */
    DictDevice detail(String deviceDictId);

    /**
     * 新增设备字典
     * @param deviceDict
     * @return
     */
    int create(DictDevice deviceDict);

    /**
     * 修改设备字典
     * @param deviceDict
     * @return
     */
    int update(DictDevice deviceDict);

    /**
     * 删除设备字典
     * @param deviceDictIds
     * @return
     */
    int delete(Set<String> deviceDictIds);


}
