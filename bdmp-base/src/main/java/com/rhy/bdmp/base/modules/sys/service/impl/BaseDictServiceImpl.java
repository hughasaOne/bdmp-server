package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import com.rhy.bdmp.base.modules.sys.dao.BaseDictDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseDictService;
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
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service("baseSysDictService")
public class BaseDictServiceImpl extends ServiceImpl<BaseDictDao, Dict> implements IBaseDictService {

    /**
     * 系统字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Dict> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Dict> query = new Query<Dict>(queryVO);
            QueryWrapper<Dict> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Dict> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
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
    public PageUtil<Dict> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Dict> query = new Query<Dict>(queryVO);
            Page<Dict> page = query.getPage();
            QueryWrapper<Dict> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Dict>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看系统字典(根据ID)
     * @param dictId
     * @return
     */
    @Override
    public Dict detail(String dictId) {
        if (!StrUtil.isNotBlank(dictId)) {
            throw new BadRequestException("not exist dictId");
        }
        Dict dict = getBaseMapper().selectById(dictId);
        return dict;
    }

    /**
     * 新增系统字典
     * @param dict
     * @return
     */
    @Override
    public int create(Dict dict) {
        if (StrUtil.isNotBlank(dict.getDictId())) {
            throw new BadRequestException("A new Dict cannot already have an dictId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        dict.setCreateBy(currentUser);
        dict.setCreateTime(currentDateTime);
        dict.setUpdateBy(currentUser);
        dict.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(dict);
        return result;
    }

    /**
     * 修改系统字典
     * @param dict
     * @return
     */
    @Override
    public int update(Dict dict) {
       if (!StrUtil.isNotBlank(dict.getDictId())) {
           throw new BadRequestException("A new Dict not exist dictId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       dict.setUpdateBy(currentUser);
       dict.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(dict);
       return result;
    }

    /**
     * 删除系统字典
     * @param dictIds
     * @return
     */
    @Override
    public int delete(Set<String> dictIds) {
        int result = getBaseMapper().deleteBatchIds(dictIds);
        return result;
    }

}
