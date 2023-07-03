package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.CommonBusiness;
import com.rhy.bdmp.base.modules.sys.dao.BaseCommonBusinessDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseCommonBusinessService;
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
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Service
public class BaseCommonBusinessServiceImpl extends ServiceImpl<BaseCommonBusinessDao, CommonBusiness> implements IBaseCommonBusinessService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<CommonBusiness> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<CommonBusiness> query = new Query<CommonBusiness>(queryVO);
            QueryWrapper<CommonBusiness> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<CommonBusiness> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<CommonBusiness> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<CommonBusiness> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<CommonBusiness> query = new Query<CommonBusiness>(queryVO);
            Page<CommonBusiness> page = query.getPage();
            QueryWrapper<CommonBusiness> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<CommonBusiness>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param commonBusinessId
     * @return
     */
    @Override
    public CommonBusiness detail(String commonBusinessId) {
        if (!StrUtil.isNotBlank(commonBusinessId)) {
            throw new BadRequestException("not exist commonBusinessId");
        }
        CommonBusiness commonBusiness = getBaseMapper().selectById(commonBusinessId);
        return commonBusiness;
    }

    /**
     * 新增
     * @param commonBusiness
     * @return
     */
    @Override
    public int create(CommonBusiness commonBusiness) {
        if (StrUtil.isNotBlank(commonBusiness.getCommonBusinessId())) {
            throw new BadRequestException("A new CommonBusiness cannot already have an commonBusinessId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        commonBusiness.setCreateBy(currentUser);
        commonBusiness.setCreateTime(currentDateTime);
        commonBusiness.setUpdateBy(currentUser);
        commonBusiness.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(commonBusiness);
        return result;
    }

    /**
     * 修改
     * @param commonBusiness
     * @return
     */
    @Override
    public int update(CommonBusiness commonBusiness) {
       if (!StrUtil.isNotBlank(commonBusiness.getCommonBusinessId())) {
           throw new BadRequestException("A new CommonBusiness not exist commonBusinessId");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       commonBusiness.setUpdateBy(currentUser);
       commonBusiness.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(commonBusiness);
       return result;
    }

    /**
     * 删除
     * @param commonBusinessIds
     * @return
     */
    @Override
    public int delete(Set<String> commonBusinessIds) {
        int result = getBaseMapper().deleteBatchIds(commonBusinessIds);
        return result;
    }

}
