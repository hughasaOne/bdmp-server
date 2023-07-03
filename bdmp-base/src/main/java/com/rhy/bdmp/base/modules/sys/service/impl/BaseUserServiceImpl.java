package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.base.modules.sys.dao.BaseUserDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserService;
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
 * @description 用户 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, User> implements IBaseUserService {

    /**
     * 用户列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<User> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<User> query = new Query<User>(queryVO);
            QueryWrapper<User> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<User> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 用户列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<User> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<User> query = new Query<User>(queryVO);
            Page<User> page = query.getPage();
            QueryWrapper<User> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<User>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看用户(根据ID)
     * @param userId
     * @return
     */
    @Override
    public User detail(String userId) {
        if (!StrUtil.isNotBlank(userId)) {
            throw new BadRequestException("not exist userId");
        }
        User user = getBaseMapper().selectById(userId);
        return user;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public int create(User user) {
        if (StrUtil.isNotBlank(user.getUserId())) {
            throw new BadRequestException("A new User cannot already have an userId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        user.setCreateBy(currentUser);
        user.setCreateTime(currentDateTime);
        user.setUpdateBy(currentUser);
        user.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(user);
        return result;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Override
    public int update(User user) {
       if (!StrUtil.isNotBlank(user.getUserId())) {
           throw new BadRequestException("A new User not exist userId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       user.setUpdateBy(currentUser);
       user.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(user);
       return result;
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @Override
    public int delete(Set<String> userIds) {
        int result = getBaseMapper().deleteBatchIds(userIds);
        return result;
    }

}
