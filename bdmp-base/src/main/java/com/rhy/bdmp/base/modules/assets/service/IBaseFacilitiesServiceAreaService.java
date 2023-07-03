package com.rhy.bdmp.base.modules.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;

import java.util.List;
import java.util.Set;

/**
 * @author yanggj
 * @version V1.0
 * @description 服务接口
 * @date 2021-10-22 11:44
 **/
public interface IBaseFacilitiesServiceAreaService extends IService<FacilitiesServiceArea> {

    /**
     * 列表查询
     *
     * @param queryVO 查询条件
     * @return
     */
    List<FacilitiesServiceArea> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     *
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<FacilitiesServiceArea> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     *
     * @param serviceAreaId
     * @return
     */
    FacilitiesServiceArea detail(String serviceAreaId);

    /**
     * 新增
     *
     * @param facilitiesServiceArea
     * @return
     */
    int create(FacilitiesServiceArea facilitiesServiceArea);

    /**
     * 修改
     *
     * @param facilitiesServiceArea
     * @return
     */
    int update(FacilitiesServiceArea facilitiesServiceArea);

    /**
     * 删除
     *
     * @param serviceAreaIds
     * @return
     */
    int delete(Set<Integer> serviceAreaIds);


}
