package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseFacilitiesGantryService extends IService<FacilitiesGantry> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<FacilitiesGantry> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<FacilitiesGantry> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param gantryId
     * @return
     */
    FacilitiesGantry detail(String gantryId);

    /**
     * 新增
     * @param facilitiesGantry
     * @return
     */
    int create(FacilitiesGantry facilitiesGantry);

    /**
     * 修改
     * @param facilitiesGantry
     * @return
     */
    int update(FacilitiesGantry facilitiesGantry);

    /**
     * 删除
     * @param gantryIds
     * @return
     */
    int delete(Set<String> gantryIds);


}
