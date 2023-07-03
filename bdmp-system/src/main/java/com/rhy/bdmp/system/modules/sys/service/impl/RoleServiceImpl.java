package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.sys.dao.AppDao;
import com.rhy.bdmp.system.modules.sys.dao.RoleDao;
import com.rhy.bdmp.system.modules.sys.dao.UserDao;
import com.rhy.bdmp.system.modules.sys.domain.vo.RoleVo;
import com.rhy.bdmp.system.modules.sys.service.RoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Resource
    private AppDao appDao;

    @Resource
    private UserDao userDao;

    //基础数据管理平台appID
    private static final String BDMPAPPID = "1";

    /**
    * @Description: 删除角色
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    @Transactional
    @Override
    public void delete(Set<String> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            //如果只传一个id，判断id是否存在
            if (roleIds.size() == 1) {
                if (roleDao.selectById(String.valueOf(roleIds.toArray()[0])) == null) {
                    throw new BadRequestException("角色ID不存在");
                }
            }
            //删除角色
            roleDao.deleteBatchIds(roleIds);
            // 同时删除角色资源权限表
            roleDao.deleteRoleResourceByRoleIds(roleIds);
            // 删除用户角色关系表
            roleDao.deleteUserRoleByRoleIds(roleIds);
        }
    }

    /**
    * @Description: 更新用户角色权限（根据角色ID更新）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    @Transactional
    @Override
    public void updateUseRoleByRoleId(String roleId, String appId, Set<String> userIds) {
        if (StrUtil.isBlank(appId)) {
            appId = BDMPAPPID;
        }
        //角色id不能为空
        if (StringUtils.isNotBlank(roleId)) {
            //删除用户角色表（根据角色Id、应用Id）
            roleDao.deleteUserRoleByRoleIdAndAppId(roleId,appId);
            //用户ID集合不能为空
            if (CollectionUtils.isNotEmpty(userIds)) {
                String createBy = WebUtils.getUserId();
                Date createTime = DateUtil.date();
                //根据角色ID添加用户
                roleDao.insertUserRoleByRoleId(roleId, userIds, createBy, createTime);
            }
        }
    }

    /**
     * @Description: 更新角色资源权限（根据角色ID更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Transactional
    @Override
    public void updateRoleResourceByRoleIdAndAppId(String roleId, String appId, Set<String> resourceIds) {
        //角色id不能为空
        if (StringUtils.isNotBlank(roleId)) {
            if (StringUtils.isNotBlank(appId)) {
                //删除角色资源表（根据角色ID、应用ID）
                roleDao.deleteRoleResourceByRoleIdAndAppId(roleId, appId);
                //资源ID集合不能为空
                if (CollectionUtils.isNotEmpty(resourceIds)) {
                    String createBy = WebUtils.getUserId();
                    Date createTime = DateUtil.date();
                    //根据用户ID添加资源
                    roleDao.insertRoleResourceByRoleId(roleId, resourceIds, createBy, createTime);
                }
            } else {
                throw new BadRequestException("应用ID不能为空");
            }
        }else {
            throw new BadRequestException("角色ID不能为空");
        }
    }

    /**
     * @Description: 查询用户角色权限（根据角色ID，应用ID）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Override
    public List<String> findUserIdsByRoleIdAndAppId(String roleId, String appId) {
        //如果appId为空默认appId为基础数据平台应用ID
        if (StrUtil.isBlank(appId)) {
            appId = BDMPAPPID;
        }
        List<String> result = new ArrayList<>();
        if (StrUtil.isNotBlank(roleId)) {
            //根据角色ID、应用ID查询用户角色权限表(返回用户IDs)
            result = roleDao.findUserIdsByRoleIdAndAppId(roleId, appId);
        }
        return result;
    }

    /**
     * @Description: 分配资源权限时查询角色资源权限（根据角色ID,返回资源ID集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Override
    public List<String> findResourceIdsByRoleIdAndAppId(String roleId, String appId) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(roleId) && StrUtil.isNotBlank(appId)) {
            //根据角色ID和应用ID查询role_resource表，返回资源ID(只返回子节点)
            result = roleDao.findResourceIdsByRoleIdAndAppIdWithoutParentId(roleId, appId);
            //去重
            result = result.stream().distinct().collect(Collectors.toList());
        }
        return result;
    }

    /**
     * @Description: 查询应用角色树
     * @Author: dongyu
     * @Date: 2021/4/15
     * @param appIds
     */
    @Override
    public List<NodeVo> findAppRoleTree(Set<String> appIds) {
        List<NodeVo> result = new ArrayList<>();
        //获取当前用户id
        String userId = WebUtils.getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new BadRequestException("未获取到当前用户");
        }
        List<App> appList = new ArrayList<>();
        List<Role> roleVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(appIds)) {
            //查询应用
            appList= appDao.selectList(new QueryWrapper<App>().in("app_id",appIds).orderByAsc("sort").orderByDesc("create_time"));
            //根据应用查询角色
            roleVoList = roleDao.selectList(new QueryWrapper<Role>().in("app_id", appIds).orderByAsc("sort").orderByDesc("create_time"));

        }else{
            //查所有
            //查询应用
            appList = appDao.selectList(new QueryWrapper<App>().orderByAsc("sort").orderByDesc("create_time"));
            //查询角色
            roleVoList = roleDao.selectList(new QueryWrapper<Role>().orderByAsc("sort").orderByDesc("create_time"));
        }
        if (CollectionUtils.isNotEmpty(appList)) {
            //app转为Node
            for (App appVo : appList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(appVo.getAppId());
                nodeVo.setLabel(appVo.getAppName());
                nodeVo.setValue(appVo.getAppId());
                nodeVo.setNoteType("app");
                nodeVo.setMoreInfo(appVo);
                result.add(nodeVo);
            }
        }
        if (CollectionUtils.isNotEmpty(roleVoList)) {
            //role转为Node
            for (Role roleVo : roleVoList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(roleVo.getRoleId());
                nodeVo.setLabel(roleVo.getRoleName());
                nodeVo.setValue(roleVo.getRoleId());
                nodeVo.setParentId(roleVo.getAppId());
                nodeVo.setNoteType("role");
                nodeVo.setMoreInfo(roleVo);
                result.add(nodeVo);
            }
        }
        return result;
    }

    /**
    * @Description: 新增角色
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    @Override
    public int create(Role role) {
        if (StrUtil.isNotBlank(role.getRoleId())) {
            throw new BadRequestException("角色ID已存在，不能做新增操作");
        }
        //appId是否存在
        if (StrUtil.isNotBlank(role.getAppId())) {
            if (appDao.selectById(role.getAppId()) == null) {
                throw new BadRequestException("appId不存在");
            }
        }
        //datastatusid默认为1
        if (role.getDatastatusid() == null) {
            role.setDatastatusid(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        role.setCreateBy(currentUser);
        role.setCreateTime(currentDateTime);
        role.setUpdateBy(currentUser);
        role.setUpdateTime(currentDateTime);
        int result = roleDao.insert(role);
        return result;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public int update(Role role) {
        if (StrUtil.isBlank(role.getRoleId())) {
            throw new BadRequestException("角色ID不能为空");
        }else {
            //角色ID不存在
            if (roleDao.selectById(role.getRoleId()) == null) {
                throw new BadRequestException("角色ID不存在");
            }
        }
        //appId是否存在
        if (role.getAppId() != null) {
            if (appDao.selectById(role.getAppId()) == null) {
                throw new BadRequestException("appId不存在");
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        role.setUpdateBy(currentUser);
        role.setUpdateTime(currentDateTime);
        int result = roleDao.updateById(role);
        return result;
    }

    /**
     * 角色列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public Object list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Role> query = new Query<Role>(queryVO);
            Page<Role> page = query.getPage();
            QueryWrapper<Role> queryWrapper = query.getQueryWrapper();
            queryWrapper.orderByAsc("sort").orderByDesc("create_time");
            if (null != page) {
                page = roleDao.selectPage(page, queryWrapper);
                //获取Role
                List<Role> roleList = page.getRecords();
                //Role转换成RoleVo
                List<RoleVo> roleVoList = convertVo(roleList);
                Page<RoleVo> roleVoPage = new Page<>();
                //Role的分页信息复制到RoleVo的分页信息
                BeanUtils.copyProperties(page, roleVoPage);
                roleVoPage.setRecords(roleVoList);
                return roleVoPage;
            }
            List<Role> entityList = roleDao.selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(roleDao.selectList(new QueryWrapper<Role>().orderByAsc("sort").orderByDesc("create_time")));
    }

    /**
     * 查看角色(根据ID)
     * @param roleId
     * @return
     */
    @Override
    public RoleVo detail(String roleId) {
        if (!StrUtil.isNotBlank(roleId)) {
            throw new BadRequestException("角色ID不能为空");
        }
        Role role = roleDao.selectById(roleId);
        if (role != null) {
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            return convertVo(roles).get(0);
        }
        return null;
    }

    /**
     * Role转换成RoleVo
     * @param roleList
     * @return
     */
    public List<RoleVo> convertVo(List<Role> roleList) {
        //应用列表
        List<App> apps = appDao.selectList(new QueryWrapper<App>());
        //角色Vo列表
        List<RoleVo> roleVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (Role role : roleList) {
                RoleVo roleVo = new RoleVo();
                BeanUtils.copyProperties(role, roleVo);
                if (CollectionUtils.isNotEmpty(apps)) {
                    //所属应用名称
                    apps.forEach(o -> {
                        if (o.getAppId().equals(role.getAppId())) {
                            roleVo.setAppName(o.getAppName());
                        }
                    });
                }
                roleVoList.add(roleVo);
            }
        }
        return roleVoList;
    }
}
