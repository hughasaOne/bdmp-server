package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
import com.rhy.bdmp.base.modules.sys.dao.BaseAuthClientDetailsDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseAuthClientDetailsService;
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
 * @description oauth2认证配置 服务实现
 * @author shuaichao
 * @date 2022-03-23 17:22
 * @version V1.0
 **/
@Service
public class BaseAuthClientDetailsServiceImpl extends ServiceImpl<BaseAuthClientDetailsDao, AuthClientDetails> implements IBaseAuthClientDetailsService {

    /**
     * oauth2认证配置列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<AuthClientDetails> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<AuthClientDetails> query = new Query<AuthClientDetails>(queryVO);
            QueryWrapper<AuthClientDetails> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                queryWrapper.orderByAsc("sort");
                List<AuthClientDetails> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<AuthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.orderByAsc("sort");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * oauth2认证配置列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<AuthClientDetails> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<AuthClientDetails> query = new Query<AuthClientDetails>(queryVO);
            Page<AuthClientDetails> page = query.getPage();
            QueryWrapper<AuthClientDetails> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                queryWrapper.orderByAsc("sort");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<AuthClientDetails>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看oauth2认证配置(根据ID)
     * @param id
     * @return
     */
    @Override
    public AuthClientDetails detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        AuthClientDetails authClientDetails = getBaseMapper().selectById(id);
        return authClientDetails;
    }

    /**
     * 新增oauth2认证配置
     * @param authClientDetails
     * @return
     */
    @Override
    public int create(AuthClientDetails authClientDetails) {
        if (StrUtil.isNotBlank(authClientDetails.getId())) {
            throw new BadRequestException("A new AuthClientDetails cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        authClientDetails.setCreateBy(currentUser);
        authClientDetails.setCreateTime(currentDateTime);
        authClientDetails.setUpdateBy(currentUser);
        authClientDetails.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(authClientDetails);
        return result;
    }

    /**
     * 修改oauth2认证配置
     * @param authClientDetails
     * @return
     */
    @Override
    public int update(AuthClientDetails authClientDetails) {
       if (!StrUtil.isNotBlank(authClientDetails.getId())) {
           throw new BadRequestException("A new AuthClientDetails not exist id");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       authClientDetails.setUpdateBy(currentUser);
       authClientDetails.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(authClientDetails);
       return result;
    }

    /**
     * 删除oauth2认证配置
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
