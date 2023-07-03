package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceGridVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
@Mapper
public interface DeviceDao extends BaseMapper<Dict> {

    /**
     * 查询设备（分页）
     *
     * @param page                 分页对象
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限等级
     * @param nodeType             节点类型(org,way,fac)
     * @param nodeId               节点ID
     * @param deviceType           设备类型
     * @param systemIds            系统ID
     * @param deviceName           设备名称
     * @return /
     */
    <E extends IPage<DeviceGridVo>> E queryPage(IPage<DeviceGridVo> page,
                                                @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                @Param("userId") String userId,
                                                @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                @Param("nodeType") String nodeType,
                                                @Param("nodeId") String nodeId,
                                                @Param("deviceType") String deviceType,
                                                @Param("systemIds") List<String> systemIds,
                                                @Param("deviceName") String deviceName,@Param("deviceCode") String deviceCode);


    /**
     * 删除设备拓展表
     *
     * @param mapList
     */
    int deleteDeviceExtTable(@Param("mapList") List<Map<String, String>> mapList);

    /*
     * 根据用户id获取用户对象
     * */
    User getUserById(String userId);

    /**
     * 根据设施id查找设备管理单位
     *
     * @param facilitiesId
     * @return
     */
    List<Org> findManageDepartment(@Param("facilitiesId") String facilitiesId);

    /**
     * 查询没有设施Id的设备
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     */
    Page<DeviceGridVo> getDeviceNoFacId(Page<DeviceGridVo> page, Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel);

    /**
     * 查询刚同步的终端箱
     */
    List<Device> getCurSynBox();
}
