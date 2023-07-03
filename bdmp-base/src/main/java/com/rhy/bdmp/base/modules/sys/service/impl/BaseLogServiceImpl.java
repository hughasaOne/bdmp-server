package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.dao.BaseLogDao;
import com.rhy.bdmp.base.modules.sys.domain.po.Log;
import com.rhy.bdmp.base.modules.sys.service.IBaseLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 日志 服务实现
 * @author weicaifu
 * @date 2022-10-17 17:21
 * @version V1.0
 **/
@Service
public class BaseLogServiceImpl extends ServiceImpl<BaseLogDao, Log> implements IBaseLogService {

    /**
     * 日志列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Log> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Log> query = new Query<Log>(queryVO);
            QueryWrapper<Log> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                List<Log> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 日志列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Log> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Log> query = new Query<Log>(queryVO);
            Page<Log> page = query.getPage();
            QueryWrapper<Log> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Log>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看日志(根据ID)
     * @param logId
     * @return
     */
    @Override
    public Log detail(String logId) {
        if (!StrUtil.isNotBlank(logId)) {
            throw new BadRequestException("not exist logId");
        }
        Log log = getBaseMapper().selectById(logId);
        return log;
    }

    /**
     * 新增日志
     * @param log
     * @return
     */
    @Override
    public int create(Log log) {
        if (StrUtil.isNotBlank(log.getLogId())) {
            throw new BadRequestException("A new Log cannot already have an logId");
        }
        Date currentDateTime = DateUtil.date();
        log.setCreateTime(currentDateTime);
        int result = getBaseMapper().insert(log);
        return result;
    }

    /**
     * 修改日志
     * @param log
     * @return
     */
    @Override
    public int update(Log log) {
       if (!StrUtil.isNotBlank(log.getLogId())) {
           throw new BadRequestException("A new Log not exist logId");
       }
       int result = getBaseMapper().updateById(log);
       return result;
    }

    /**
     * 删除日志
     * @param logIds
     * @return
     */
    @Override
    public int delete(Set<String> logIds) {
        int result = getBaseMapper().deleteBatchIds(logIds);
        return result;
    }

}
