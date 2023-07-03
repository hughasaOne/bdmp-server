package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author shuaichao
 * @date 2022-03-21 10:14
 * @version V1.0
 **/
public interface IBaseSysBusinessService extends IService<SysBusiness> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<SysBusiness> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<SysBusiness> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param businessId
     * @return
     */
    SysBusiness detail(String businessId);

    /**
     * 新增
     * @param sysBusiness
     * @return
     */
    int create(SysBusiness sysBusiness);

    /**
     * 修改
     * @param sysBusiness
     * @return
     */
    int update(SysBusiness sysBusiness);

    /**
     * 删除
     * @param businessIds
     * @return
     */
    int delete(Set<String> businessIds);


}
