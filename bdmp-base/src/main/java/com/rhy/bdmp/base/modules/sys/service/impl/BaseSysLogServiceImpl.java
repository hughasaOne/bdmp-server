package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.SysLog;
import com.rhy.bdmp.base.modules.sys.dao.BaseSysLogDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description  服务实现
 * @author shuaichao
 * @date 2022-03-17 11:39
 * @version V1.0
 **/
@Service
public class BaseSysLogServiceImpl extends ServiceImpl<BaseSysLogDao, SysLog> implements IBaseSysLogService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<SysLog> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<SysLog> query = new Query<SysLog>(queryVO);
            QueryWrapper<SysLog> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                List<SysLog> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<SysLog> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<SysLog> query = new Query<SysLog>(queryVO);
            Page<SysLog> page = query.getPage();
            QueryWrapper<SysLog> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<SysLog>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param sysLogId
     * @return
     */
    @Override
    public SysLog detail(String sysLogId) {
        if (!StrUtil.isNotBlank(sysLogId)) {
            throw new BadRequestException("not exist sysLogId");
        }
        SysLog sysLog = getBaseMapper().selectById(sysLogId);
        return sysLog;
    }

    /**
     * 新增
     * @param sysLog
     * @return
     */
    @Override
    public int create(SysLog sysLog) {
        if (StrUtil.isNotBlank(sysLog.getSysLogId())) {
            throw new BadRequestException("A new SysLog cannot already have an sysLogId");
        }
        Date currentDateTime = DateUtil.date();
        sysLog.setCreateTime(currentDateTime);
        int result = getBaseMapper().insert(sysLog);
        return result;
    }

    /**
     * 修改
     * @param sysLog
     * @return
     */
    @Override
    public int update(SysLog sysLog) {
       if (!StrUtil.isNotBlank(sysLog.getSysLogId())) {
           throw new BadRequestException("A new SysLog not exist sysLogId");
       }
       int result = getBaseMapper().updateById(sysLog);
       return result;
    }

    /**
     * 删除
     * @param sysLogIds
     * @return
     */
    @Override
    public int delete(Set<String> sysLogIds) {
        int result = getBaseMapper().deleteBatchIds(sysLogIds);
        return result;
    }

}
