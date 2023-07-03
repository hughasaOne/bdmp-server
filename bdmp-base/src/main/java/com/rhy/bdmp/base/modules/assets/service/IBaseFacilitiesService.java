package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 路段设施 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseFacilitiesService extends IService<Facilities> {

    /**
     * 路段设施列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Facilities> list(QueryVO queryVO);

    /**
     * 路段设施列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Facilities> page(QueryVO queryVO);

    /**
     * 查看路段设施(根据ID)
     * @param facilitiesId
     * @return
     */
    Facilities detail(String facilitiesId);

    /**
     * 新增路段设施
     * @param facilities
     * @return
     */
    int create(Facilities facilities);

    /**
     * 修改路段设施
     * @param facilities
     * @return
     */
    int update(Facilities facilities);

    /**
     * 删除路段设施
     * @param facilitiesIds
     * @return
     */
    int delete(Set<String> facilitiesIds);


}
