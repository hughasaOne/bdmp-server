package com.rhy.bdmp.system.modules.assets.service;


import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.system.modules.assets.domain.bo.SavePeripheralsBo;
import com.rhy.bdmp.system.modules.assets.domain.vo.BoxEnablePortVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.BoxPortInfoVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 终端箱外接设备业务逻辑层
 * @author 魏财富
 */
public interface BoxExternalService {
    /**
     * 获取可用的端口号和可用的外设
     * @param boxId 终端箱Id
     * @param facId 设施Id
     */
    BoxEnablePortVo getEnablePortsAndPer(String boxId,String facId);

    /**
     * 查询当前终端箱的端口情况（端口号+已关联的设备信息）
     * @param deviceId 终端箱Id
     * @param sn 终端箱sn
     */
    List<BoxPortInfoVo> getPortInfo(String deviceId,String sn);

    /**
     * 查询当前终端箱具体端口情况
     * @param boxId 终端箱Id
     * @param portNum 端口号
     */
    public Map<String,String> getPortInfoByPortAndId(String boxId, String portNum);

    /**
     * 新增外接设备
     */
    Boolean save(List<SavePeripheralsBo> savePeripheralsBoList);

    /**
     * 修改外接设备
     * @param boxId 终端箱Id
     * @param portNum 端口号
     * @param externalId 外设Id
     */
    Integer update(String boxId, String portNum,String deviceId, String externalId);

    /**
     * 解除外设关系
     * @param externalIds 外设表Id
     */
    Integer delPerRelation(Set<String> externalIds);

    /**
     * 测试终端箱与外设的网络是否能够通信
     * @param deviceIp 外设Ip
     */
    Boolean pingTest(String deviceIp);
}
