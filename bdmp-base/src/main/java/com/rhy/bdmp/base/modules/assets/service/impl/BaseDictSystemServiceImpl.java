package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictSystemDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictSystemService;
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
 * @description 系统字典 服务实现
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
@Service
public class BaseDictSystemServiceImpl extends ServiceImpl<BaseDictSystemDao, DictSystem> implements IBaseDictSystemService {

    /**
     * 系统字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DictSystem> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictSystem> query = new Query<DictSystem>(queryVO);
            QueryWrapper<DictSystem> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DictSystem> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DictSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 系统字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DictSystem> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictSystem> query = new Query<DictSystem>(queryVO);
            Page<DictSystem> page = query.getPage();
            QueryWrapper<DictSystem> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DictSystem>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看系统字典(根据ID)
     * @param systemId
     * @return
     */
    @Override
    public DictSystem detail(String systemId) {
        if (!StrUtil.isNotBlank(systemId)) {
            throw new BadRequestException("not exist systemId");
        }
        DictSystem dictSystem = getBaseMapper().selectById(systemId);
        return dictSystem;
    }

    /**
     * 新增系统字典
     * @param dictSystem
     * @return
     */
    @Override
    public int create(DictSystem dictSystem) {
        if (StrUtil.isNotBlank(dictSystem.getSystemId())) {
            throw new BadRequestException("A new DictSystem cannot already have an systemId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        dictSystem.setCreateBy(currentUser);
        dictSystem.setCreateTime(currentDateTime);
        dictSystem.setUpdateBy(currentUser);
        dictSystem.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(dictSystem);
        return result;
    }

    /**
     * 修改系统字典
     * @param dictSystem
     * @return
     */
    @Override
    public int update(DictSystem dictSystem) {
       if (!StrUtil.isNotBlank(dictSystem.getSystemId())) {
           throw new BadRequestException("A new DictSystem not exist systemId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       dictSystem.setUpdateBy(currentUser);
       dictSystem.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(dictSystem);
       return result;
    }

    /**
     * 删除系统字典
     * @param systemIds
     * @return
     */
    @Override
    public int delete(Set<String> systemIds) {
        int result = getBaseMapper().deleteBatchIds(systemIds);
        return result;
    }

}
