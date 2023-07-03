package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 桥梁 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseFacilitiesBridgeService extends IService<FacilitiesBridge> {

    /**
     * 桥梁列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<FacilitiesBridge> list(QueryVO queryVO);

    /**
     * 桥梁列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<FacilitiesBridge> page(QueryVO queryVO);

    /**
     * 查看桥梁(根据ID)
     * @param bridgeId
     * @return
     */
    FacilitiesBridge detail(String bridgeId);

    /**
     * 新增桥梁
     * @param facilitiesBridge
     * @return
     */
    int create(FacilitiesBridge facilitiesBridge);

    /**
     * 修改桥梁
     * @param facilitiesBridge
     * @return
     */
    int update(FacilitiesBridge facilitiesBridge);

    /**
     * 删除桥梁
     * @param bridgeIds
     * @return
     */
    int delete(Set<String> bridgeIds);


}
