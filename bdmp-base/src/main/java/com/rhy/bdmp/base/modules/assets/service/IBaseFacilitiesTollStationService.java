package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;
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
public interface IBaseFacilitiesTollStationService extends IService<FacilitiesTollStation> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<FacilitiesTollStation> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<FacilitiesTollStation> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param tollStationId
     * @return
     */
    FacilitiesTollStation detail(String tollStationId);

    /**
     * 新增
     * @param facilitiesTollStation
     * @return
     */
    int create(FacilitiesTollStation facilitiesTollStation);

    /**
     * 修改
     * @param facilitiesTollStation
     * @return
     */
    int update(FacilitiesTollStation facilitiesTollStation);

    /**
     * 删除
     * @param tollStationIds
     * @return
     */
    int delete(Set<String> tollStationIds);


}
