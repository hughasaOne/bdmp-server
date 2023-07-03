package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 组织机构 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseOrgService extends IService<Org> {

    /**
     * 组织机构列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Org> list(QueryVO queryVO);

    /**
     * 组织机构列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Org> page(QueryVO queryVO);

    /**
     * 查看组织机构(根据ID)
     * @param orgId
     * @return
     */
    Org detail(String orgId);

    /**
     * 新增组织机构
     * @param org
     * @return
     */
    int create(Org org);

    /**
     * 修改组织机构
     * @param org
     * @return
     */
    int update(Org org);

    /**
     * 删除组织机构
     * @param orgIds
     * @return
     */
    int delete(Set<String> orgIds);


}
