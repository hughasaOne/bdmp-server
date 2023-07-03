package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author shuaichao
 * @date 2022-03-17 11:39
 * @version V1.0
 **/
public interface IBaseSysLogService extends IService<SysLog> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<SysLog> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<SysLog> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param sysLogId
     * @return
     */
    SysLog detail(String sysLogId);

    /**
     * 新增
     * @param sysLog
     * @return
     */
    int create(SysLog sysLog);

    /**
     * 修改
     * @param sysLog
     * @return
     */
    int update(SysLog sysLog);

    /**
     * 删除
     * @param sysLogIds
     * @return
     */
    int delete(Set<String> sysLogIds);


}
