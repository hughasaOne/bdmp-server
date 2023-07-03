package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
@Mapper
public interface FacilitiesDao extends BaseMapper<Facilities> {

    /**
     * 查询路段设施（分页）
     * @param page 分页对象
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId 用户ID
     * @param dataPermissionsLevel 数据权限等级
     * @param nodeType 节点类型(org,way,fac)
     * @param nodeId 节点ID
     * @param facilitiesType 设施类型
     * @param facilitiesName 设施名称
     * @return
     */
    <E extends IPage<FacilitiesVo>> E query(IPage<FacilitiesVo> page, @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                            @Param("userId")String userId, @Param("dataPermissionsLevel")Integer dataPermissionsLevel,
                                            @Param("nodeType")String nodeType, @Param("nodeId")String nodeId,
                                            @Param("facilitiesType")String facilitiesType, @Param("facilitiesName")String facilitiesName,
                                            @Param("isShowChildren")Boolean isShowChildren,@Param("parentId") String parentId);

    /**
     * 查询路段设施（不分页）
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId 用户ID
     * @param dataPermissionsLevel 数据权限等级
     * @param nodeType 节点类型(org,way,fac)
     * @param nodeId 节点ID
     * @param facilitiesType 设施类型
     * @param facilitiesName 设施名称
     * @return
     */
    List<FacilitiesVo> query(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                             @Param("userId")String userId,
                             @Param("dataPermissionsLevel")Integer dataPermissionsLevel,
                             @Param("nodeType")String nodeType,
                             @Param("nodeId")String nodeId,
                             @Param("facilitiesType")String facilitiesType,
                             @Param("facilitiesName")String facilitiesName,
                             @Param("isShowChildren")Boolean isShowChildren,@Param("parentId") String parentId);


    /**
     * 更新子设施所属路段
     * @param facilitiesId 设施ID
     * @param waySectionId 路段ID
     * @return
     */
    int updateWaySectionByFacChlild(@Param("facilitiesId") String  facilitiesId, @Param("waySectionId") String  waySectionId);
    /*
     * 根据用户id获取用户对象
     * */
    User getUserById(String userId);

    /**
     * 根据路段查收费站集合
     * @param waysectionId
     * @return
     */
    List<Facilities> findTollStations(String waysectionId);
}
