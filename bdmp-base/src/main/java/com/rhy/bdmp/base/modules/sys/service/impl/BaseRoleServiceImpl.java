package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import com.rhy.bdmp.base.modules.sys.dao.BaseRoleDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseRoleService;
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
 * @description 角色 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleDao, Role> implements IBaseRoleService {

    /**
     * 角色列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Role> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Role> query = new Query<Role>(queryVO);
            QueryWrapper<Role> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Role> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 角色列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Role> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Role> query = new Query<Role>(queryVO);
            Page<Role> page = query.getPage();
            QueryWrapper<Role> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Role>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看角色(根据ID)
     * @param roleId
     * @return
     */
    @Override
    public Role detail(String roleId) {
        if (!StrUtil.isNotBlank(roleId)) {
            throw new BadRequestException("not exist roleId");
        }
        Role role = getBaseMapper().selectById(roleId);
        return role;
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @Override
    public int create(Role role) {
        if (StrUtil.isNotBlank(role.getRoleId())) {
            throw new BadRequestException("A new Role cannot already have an roleId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        role.setCreateBy(currentUser);
        role.setCreateTime(currentDateTime);
        role.setUpdateBy(currentUser);
        role.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(role);
        return result;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public int update(Role role) {
       if (!StrUtil.isNotBlank(role.getRoleId())) {
           throw new BadRequestException("A new Role not exist roleId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       role.setUpdateBy(currentUser);
       role.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(role);
       return result;
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    @Override
    public int delete(Set<String> roleIds) {
        int result = getBaseMapper().deleteBatchIds(roleIds);
        return result;
    }

}
