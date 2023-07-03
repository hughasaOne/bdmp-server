package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;

import java.util.List;
import java.util.Set;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
public interface IWaynetService {

    /**
     * 删除高速路网
     * @param waynetIds
     * @return
     */
    int delete(Set<String> waynetIds);

    List<Waynet> getWaynetList();


    /**
     * 高速路网列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Waynet> page(QueryVO queryVO);

    /**
     * 新增高速路网
     * @param waynet
     * @return
     */
    int create(Waynet waynet);
}
