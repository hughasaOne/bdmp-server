package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DictBrand;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictBrandDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictBrandService;
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
 * @description 品牌字典 服务实现
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
@Service
public class BaseDictBrandServiceImpl extends ServiceImpl<BaseDictBrandDao, DictBrand> implements IBaseDictBrandService {

    /**
     * 品牌字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DictBrand> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictBrand> query = new Query<DictBrand>(queryVO);
            QueryWrapper<DictBrand> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DictBrand> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DictBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 品牌字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DictBrand> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictBrand> query = new Query<DictBrand>(queryVO);
            Page<DictBrand> page = query.getPage();
            QueryWrapper<DictBrand> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DictBrand>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看品牌字典(根据ID)
     * @param brandId
     * @return
     */
    @Override
    public DictBrand detail(String brandId) {
        if (!StrUtil.isNotBlank(brandId)) {
            throw new BadRequestException("not exist brandId");
        }
        DictBrand dictBrand = getBaseMapper().selectById(brandId);
        return dictBrand;
    }

    /**
     * 新增品牌字典
     * @param dictBrand
     * @return
     */
    @Override
    public int create(DictBrand dictBrand) {
        if (StrUtil.isNotBlank(dictBrand.getBrandId())) {
            throw new BadRequestException("A new DictBrand cannot already have an brandId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        dictBrand.setCreateBy(currentUser);
        dictBrand.setCreateTime(currentDateTime);
        dictBrand.setUpdateBy(currentUser);
        dictBrand.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(dictBrand);
        return result;
    }

    /**
     * 修改品牌字典
     * @param dictBrand
     * @return
     */
    @Override
    public int update(DictBrand dictBrand) {
       if (!StrUtil.isNotBlank(dictBrand.getBrandId())) {
           throw new BadRequestException("A new DictBrand not exist brandId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       dictBrand.setUpdateBy(currentUser);
       dictBrand.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(dictBrand);
       return result;
    }

    /**
     * 删除品牌字典
     * @param brandIds
     * @return
     */
    @Override
    public int delete(Set<String> brandIds) {
        int result = getBaseMapper().deleteBatchIds(brandIds);
        return result;
    }

}
