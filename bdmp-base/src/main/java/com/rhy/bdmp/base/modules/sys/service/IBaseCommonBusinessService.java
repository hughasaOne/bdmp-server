package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.CommonBusiness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
public interface IBaseCommonBusinessService extends IService<CommonBusiness> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<CommonBusiness> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<CommonBusiness> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param commonBusinessId
     * @return
     */
    CommonBusiness detail(String commonBusinessId);

    /**
     * 新增
     * @param commonBusiness
     * @return
     */
    int create(CommonBusiness commonBusiness);

    /**
     * 修改
     * @param commonBusiness
     * @return
     */
    int update(CommonBusiness commonBusiness);

    /**
     * 删除
     * @param commonBusinessIds
     * @return
     */
    int delete(Set<String> commonBusinessIds);


}
