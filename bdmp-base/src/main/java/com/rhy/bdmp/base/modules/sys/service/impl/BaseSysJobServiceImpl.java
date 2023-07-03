package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.rhy.bdmp.base.modules.sys.dao.BaseSysJobDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysJobService;
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
 * @date 2022-03-17 11:38
 * @version V1.0
 **/
@Service
public class BaseSysJobServiceImpl extends ServiceImpl<BaseSysJobDao, SysJob> implements IBaseSysJobService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<SysJob> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<SysJob> query = new Query<SysJob>(queryVO);
            QueryWrapper<SysJob> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                queryWrapper.orderByAsc("sort");
                List<SysJob> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.orderByAsc("sort");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<SysJob> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<SysJob> query = new Query<SysJob>(queryVO);
            Page<SysJob> page = query.getPage();
            QueryWrapper<SysJob> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                queryWrapper.orderByAsc("sort");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<SysJob>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param sysJobId
     * @return
     */
    @Override
    public SysJob detail(String sysJobId) {
        if (!StrUtil.isNotBlank(sysJobId)) {
            throw new BadRequestException("not exist sysJobId");
        }
        SysJob sysJob = getBaseMapper().selectById(sysJobId);
        return sysJob;
    }

    /**
     * 新增
     * @param sysJob
     * @return
     */
    @Override
    public int create(SysJob sysJob) {
        if (StrUtil.isNotBlank(sysJob.getSysJobId())) {
            throw new BadRequestException("A new SysJob cannot already have an sysJobId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        sysJob.setCreateBy(currentUser);
        sysJob.setCreateTime(currentDateTime);
        sysJob.setUpdateBy(currentUser);
        sysJob.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(sysJob);
        return result;
    }

    /**
     * 修改
     * @param sysJob
     * @return
     */
    @Override
    public int update(SysJob sysJob) {
       if (!StrUtil.isNotBlank(sysJob.getSysJobId())) {
           throw new BadRequestException("A new SysJob not exist sysJobId");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       sysJob.setUpdateBy(currentUser);
       sysJob.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(sysJob);
       return result;
    }

    /**
     * 删除
     * @param sysJobIds
     * @return
     */
    @Override
    public int delete(Set<String> sysJobIds) {
        int result = getBaseMapper().deleteBatchIds(sysJobIds);
        return result;
    }

}
