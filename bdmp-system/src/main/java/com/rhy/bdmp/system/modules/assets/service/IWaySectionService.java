package com.rhy.bdmp.system.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionRouteVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionVo;

import java.util.Set;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
public interface IWaySectionService {

    /**
     * 查询路段（分页）
     * @param currentPage 当前页
     * @param size 页大小
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId 组织ID
     * @param waySectionName 路段名称
     * @return
     */
    Page<WaysectionVo> queryPage(Integer currentPage, Integer size, Boolean isUseUserPermissions,
                                 String orgId, String waySectionName,String waynetId);

    /**
     * 运营路段列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Waysection> page(QueryVO queryVO);

    /**
     * 新增路段
     * @param waysection
     * @return
     */
    int create(WaysectionRouteVo waysection);

    /**
     * 删除路段
     * @param waysectionIds
     * @return
     */
    int delete(Set<String> waysectionIds);

    /**
     * 修改路段
     * @param waysection
     * @return
     */
    int update(WaysectionRouteVo waysection);

    /**
     * 查询路段详情
     * @param waysectionId
     * @return
     */
    WaysectionRouteVo detail(String waysectionId);
}
