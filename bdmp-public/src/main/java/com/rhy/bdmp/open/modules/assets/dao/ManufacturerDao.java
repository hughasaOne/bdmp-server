package com.rhy.bdmp.open.modules.assets.dao;

import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.Org;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 厂商数据接口
 * @author weicaifu
 */
@Mapper
public interface ManufacturerDao {

    /**
     * 查询用户所属厂商
     * @param userId 用户id
     */
    String getUserManufacturer(@Param("userId") String userId);


    /**
     * 集团
     * @param orgId 节点id
     * @param nodeType 节点类型
     * @param manufacturerId 厂商id
     * @param isAsync 是否异步
     */
    List<Org> getGroupManufacturer(@Param("orgId") String orgId,
                                   @Param("nodeType") String nodeType,
                                   @Param("manufacturerId") String manufacturerId,
                                   @Param("isAsync") Boolean isAsync);

    /**
     * 公司
     * @param orgId 节点id
     * @param nodeType 节点类型
     * @param manufacturerId 厂商id
     * @param isAsync 是否异步
     */
    List<Org> getOrgManufacturer(@Param("orgId") String orgId,
                                 @Param("nodeType") String nodeType,
                                 @Param("manufacturerId") String manufacturerId,
                                 @Param("isAsync") Boolean isAsync);

    /**
     * 路段
     * @param orgId 节点id
     * @param nodeType 节点类型
     * @param manufacturerId 厂商id
     * @param isAsync 是否异步
     */
    List<Waysection> getWayManufacturer(@Param("orgId") String orgId,
                                        @Param("nodeType") String nodeType,
                                        @Param("manufacturerId") String manufacturerId,
                                        @Param("isAsync") Boolean isAsync);

    /**
     * 一级设施
     * @param orgId 节点id
     * @param nodeType 节点类型
     * @param manufacturerId 厂商id
     * @param isAsync 是否异步
     * @param facilitiesTypes 设施类型
     */
    List<Facilities> getFacManufacturer(@Param("orgId") String orgId,
                                        @Param("nodeType") String nodeType,
                                        @Param("manufacturerId") String manufacturerId,
                                        @Param("isAsync") Boolean isAsync,
                                        @Param("facilitiesTypes") List<String> facilitiesTypes);

    /**
     * 二级设施
     * @param orgId 节点id
     * @param nodeType 节点类型
     * @param manufacturerId 厂商id
     * @param isAsync 是否异步
     * @param facilitiesTypes 设施类型
     */
    List<Facilities> getSubFacManufacturerTree(@Param("orgId") String orgId,
                                               @Param("nodeType") String nodeType,
                                               @Param("manufacturerId") String manufacturerId,
                                               @Param("isAsync") Boolean isAsync,
                                               @Param("facilitiesTypes") List<String> facilitiesTypes);
}
