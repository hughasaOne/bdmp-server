package com.rhy.bdmp.open.modules.device.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.common.domain.vo.TreeNode;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceListBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DevicePageBo;
import com.rhy.bdmp.open.modules.device.domain.bo.StatDeviceNumByTypeBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.device.domain.vo.InformationBoardVo;
import com.rhy.bdmp.open.modules.device.domain.vo.StatDeviceNumByTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component(value = "DeviceDaoV1")
public interface DeviceDao {
    List<StatDeviceNumByTypeVo> statDeviceNumByType(@Param("commonBo") StatDeviceNumByTypeBo commonBo,
                                                    @Param("userPermissions") UserPermissions userPermissions);

    List<DeviceVo> getDeviceList(@Param("commonBo") DeviceListBo deviceListBo,
                                 @Param("userPermissions") UserPermissions userPermissions);

    Page<DeviceVo> getDeviceList(Page<DeviceVo> page,
                                 @Param("commonBo") DevicePageBo devicePageBo,
                                 @Param("userPermissions") UserPermissions userPermissions);

    DeviceVo getDeviceDetail(@Param("deviceDetailBo") DeviceDetailBo deviceDetailBo);

    List<TreeNode> getAllNode(@Param("facIds") Set<String> facIds,
                              @Param("wayIds") Set<String> wayIds,
                              @Param("deviceTypes") Set<String> deviceTypes,
                              @Param("nodeType") String nodeType);

    InformationBoardVo getInformationBoardDetail(@Param("deviceDetailBo") DeviceDetailBo deviceDetailBo);

    DeviceVo belongToDevice(@Param("deviceId") String deviceId);
}
