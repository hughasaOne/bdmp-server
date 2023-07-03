package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import com.rhy.bdmp.base.modules.sys.dao.BaseUserMappingDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserMappingService;
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
 * @description 用户映射 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseUserMappingServiceImpl extends ServiceImpl<BaseUserMappingDao, UserMapping> implements IBaseUserMappingService {

    /**
     * 用户映射列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<UserMapping> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<UserMapping> query = new Query<UserMapping>(queryVO);
            QueryWrapper<UserMapping> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                List<UserMapping> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<UserMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 用户映射列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<UserMapping> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<UserMapping> query = new Query<UserMapping>(queryVO);
            Page<UserMapping> page = query.getPage();
            QueryWrapper<UserMapping> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<UserMapping>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看用户映射(根据ID)
     * @param userMappingId
     * @return
     */
    @Override
    public UserMapping detail(String userMappingId) {
        if (!StrUtil.isNotBlank(userMappingId)) {
            throw new BadRequestException("not exist userMappingId");
        }
        UserMapping userMapping = getBaseMapper().selectById(userMappingId);
        return userMapping;
    }

    /**
     * 新增用户映射
     * @param userMapping
     * @return
     */
    @Override
    public int create(UserMapping userMapping) {
        if (StrUtil.isNotBlank(userMapping.getUserMappingId())) {
            throw new BadRequestException("A new UserMapping cannot already have an userMappingId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        userMapping.setCreateBy(currentUser);
        userMapping.setCreateTime(currentDateTime);
        userMapping.setUpdateBy(currentUser);
        userMapping.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(userMapping);
        return result;
    }

    /**
     * 修改用户映射
     * @param userMapping
     * @return
     */
    @Override
    public int update(UserMapping userMapping) {
       if (!StrUtil.isNotBlank(userMapping.getUserMappingId())) {
           throw new BadRequestException("A new UserMapping not exist userMappingId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       userMapping.setUpdateBy(currentUser);
       userMapping.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(userMapping);
       return result;
    }

    /**
     * 删除用户映射
     * @param userMappingIds
     * @return
     */
    @Override
    public int delete(Set<String> userMappingIds) {
        int result = getBaseMapper().deleteBatchIds(userMappingIds);
        return result;
    }

}
