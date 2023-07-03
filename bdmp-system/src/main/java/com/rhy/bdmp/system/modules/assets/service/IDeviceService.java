package com.rhy.bdmp.system.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.system.modules.assets.domain.bo.RequiredFieldsBo;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceGridVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.UploadBoxEcelVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
public interface IDeviceService {

    Map<String,Object> checkRequiredFields(RequiredFieldsBo requiredFieldsBo);

    /**
     * 查询设备（分页）
     * @param currentPage 当前页
     * @param size 页大小
     * @param isUseUserPermissions 是否使用用户权限
     * @param nodeType 节点类型(org,way,fac)
     * @param nodeId 节点ID
     * @param deviceType 设备类型
     * @param systemId 系统ID
     * @param deviceName 设备名称
     * @return
     */
    Page<DeviceGridVo> queryPage(Integer currentPage, Integer size, Boolean isUseUserPermissions,
                                 String nodeType, String nodeId, String deviceType,
                                 String systemId, String deviceName,String deviceCode);

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
    DeviceVo detail(String deviceId) throws Exception;

    /**
     * 新增设备
     * @param deviceVo
     * @return
     */
    int create(DeviceVo deviceVo) throws Exception;

    /**
     * 修改设备
     * @param deviceVo
     * @return
     */
    int update(DeviceVo deviceVo) throws Exception;

    /**
     * 删除设备
     * @param deviceIds
     * @return
     */
    int delete(Set<String> deviceIds);

    /**
     * 根据设施id查找设备管理单位
     * @param facilitiesId
     * @return
     */
    List<NodeVo> findManageDepartment(String facilitiesId);

    /**
     * 查询没有设施Id的设备
     * @param currentPage 当前页
     * @param size 页大小
     * @param isUseUserPermissions 是否使用用户权限过滤
     */
    Page<DeviceGridVo> getDeviceNoFacId(Integer currentPage, Integer size, Boolean isUseUserPermissions);

    /**
     * @param nodeVo 前端传递的参数
     * @param uploadBoxEcelVolist 解析好的终端箱excel数据
     */
    String saveBatchBox(NodeVo nodeVo, List<UploadBoxEcelVo> uploadBoxEcelVolist);
}
