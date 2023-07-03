package com.rhy.bdmp.system.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yanggj
 * @Date: 2021/10/26 10:21
 * @Version: 1.0.0
 */
public interface IDictDeviceService {

    Object getDictDevice(DictBO dictBO);

    /**
     * 查询设备字典列表
     *
     * @param param /
     * @return List
     */
    List<DictDevice> list(String param);

    /**
     * 分页查询设备字典列表
     *
     * @param currentPage 当前页
     * @param size        页大小
     * @param param       /
     * @return Page
     */
    Page<DictDevice> queryPage(Integer currentPage, Integer size, String param);

    List<Map<String,Object>> getChildren(String parentId);

    /**
     * 通过id查询设备字典
     *
     * @param deviceDictId /
     * @return DeviceDict
     */
    Map<String,Object> detail(String deviceDictId);

    /**
     * 新增设备字典
     *
     * @param deviceDict /
     * @return /
     */
    boolean create(DictDevice deviceDict);

    /**
     * 更新设备字典
     *
     * @param deviceDict /
     * @return /
     */
    boolean update(DictDevice deviceDict);

    /**
     * 删除设备字典
     *
     * @param deviceDictIds /
     * @return /
     */
    int delete(Set<String> deviceDictIds);
}
