package com.rhy.bdmp.base.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.Log;

import java.util.List;
import java.util.Set;

/**
 * @description 日志 服务接口
 * @author weicaifu
 * @date 2022-10-17 17:21
 * @version V1.0
 **/
public interface IBaseLogService extends IService<Log> {

    /**
     * 日志列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Log> list(QueryVO queryVO);

    /**
     * 日志列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Log> page(QueryVO queryVO);

    /**
     * 查看日志(根据ID)
     * @param logId
     * @return
     */
    Log detail(String logId);

    /**
     * 新增日志
     * @param log
     * @return
     */
    int create(Log log);

    /**
     * 修改日志
     * @param log
     * @return
     */
    int update(Log log);

    /**
     * 删除日志
     * @param logIds
     * @return
     */
    int delete(Set<String> logIds);


}
