package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author shuaichao
 * @date 2022-03-17 11:38
 * @version V1.0
 **/
public interface IBaseSysJobService extends IService<SysJob> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<SysJob> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<SysJob> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param sysJobId
     * @return
     */
    SysJob detail(String sysJobId);

    /**
     * 新增
     * @param sysJob
     * @return
     */
    int create(SysJob sysJob);

    /**
     * 修改
     * @param sysJob
     * @return
     */
    int update(SysJob sysJob);

    /**
     * 删除
     * @param sysJobIds
     * @return
     */
    int delete(Set<String> sysJobIds);


}
