package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
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
public interface IBaseFacilitiesTunnelService extends IService<FacilitiesTunnel> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<FacilitiesTunnel> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<FacilitiesTunnel> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param tunnelId
     * @return
     */
    FacilitiesTunnel detail(String tunnelId);

    /**
     * 新增
     * @param facilitiesTunnel
     * @return
     */
    int create(FacilitiesTunnel facilitiesTunnel);

    /**
     * 修改
     * @param facilitiesTunnel
     * @return
     */
    int update(FacilitiesTunnel facilitiesTunnel);

    /**
     * 删除
     * @param tunnelIds
     * @return
     */
    int delete(Set<String> tunnelIds);


}
