package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 高速路网 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseWaynetService extends IService<Waynet> {

    /**
     * 高速路网列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Waynet> list(QueryVO queryVO);

    /**
     * 高速路网列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Waynet> page(QueryVO queryVO);

    /**
     * 查看高速路网(根据ID)
     * @param waynetId
     * @return
     */
    Waynet detail(String waynetId);

    /**
     * 新增高速路网
     * @param waynet
     * @return
     */
    int create(Waynet waynet);

    /**
     * 修改高速路网
     * @param waynet
     * @return
     */
    int update(Waynet waynet);

    /**
     * 删除高速路网
     * @param waynetIds
     * @return
     */
    int delete(Set<String> waynetIds);


}
