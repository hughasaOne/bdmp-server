package com.rhy.bdmp.base.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.WayMapping;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author weicaifu
 * @date 2023-01-09 09:19
 * @version V1.0
 **/
public interface IBaseWayMappingService extends IService<WayMapping> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<WayMapping> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<WayMapping> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param id
     * @return
     */
    WayMapping detail(String id);

    /**
     * 新增
     * @param wayMapping
     * @return
     */
    int create(WayMapping wayMapping);

    /**
     * 修改
     * @param wayMapping
     * @return
     */
    int update(WayMapping wayMapping);

    /**
     * 删除
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
