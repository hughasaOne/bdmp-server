package com.rhy.bdmp.system.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesInfoVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesVo;

import java.util.List;
import java.util.Set;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
public interface IFacilitiesService {

    /**
     * 查询路段设施（分页）
     * @param currentPage 当前页
     * @param size 页大小
     * @param isUseUserPermissions 是否使用用户权限
     * @param nodeType 节点类型(org,way,fac)
     * @param nodeId 节点ID
     * @param facilitiesType 设施类型
     * @param facilitiesName 设施名称
     * @param isShowChildren 是否获取子设施
     * @return
     */
    Page<FacilitiesVo> queryPage(Integer currentPage, Integer size, Boolean isUseUserPermissions,
                                 String nodeType, String nodeId, String facilitiesType,
                                 String facilitiesName, Boolean isShowChildren,String parentId);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Facilities> page(QueryVO queryVO);

    /**
     * 查询路段设施（不分页）
     * @param isUseUserPermissions 是否使用用户权限
     * @param nodeType 节点类型(org,way,fac)
     * @param nodeId 节点ID
     * @param facilitiesType 设施类型
     * @param facilitiesName 设施名称
     * @param isShowChildren 是否获取子设施
     * @return
     */
    List<FacilitiesVo> query(Boolean isUseUserPermissions, String nodeType, String nodeId,
                             String facilitiesType, String facilitiesName, Boolean isShowChildren,String parentId);

    /**
     * 新增路段设施
     * @param facilities
     * @return
     */
    int create(FacilitiesInfoVo facilities) throws Exception;

    /**
     * 删除路段设施
     * @param facilitiesIds
     * @return
     */
    int delete(Set<String> facilitiesIds);

    /**
     * 修改路段设施
     * @param facilities
     * @return
     */
    int update(FacilitiesInfoVo facilities) throws Exception;

    /**
     * 根据路段查收费站集合
     * @param waysectionId
     * @return
     */
    List<Facilities> findTollStations(String waysectionId);

    /**
     * 根据设施id查询设施详情
     * @param facilitiesId
     * @return
     */
    FacilitiesInfoVo detail(String facilitiesId) throws Exception;
}
