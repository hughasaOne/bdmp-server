package com.rhy.bdmp.open.modules.pts.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.pts.PageHelper;
import com.rhy.bdmp.open.modules.pts.dao.PtsSysDao;
import com.rhy.bdmp.open.modules.pts.domain.vo.UserPermissionVo;
import com.rhy.bdmp.open.modules.pts.service.IPtsSysService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: yanggj
 * @Date: 2021/12/15 17:40
 * @Version: 1.0.0
 */
@Service
public class PtsSysServiceImpl implements IPtsSysService {

    private final PtsSysDao ptsSysDao;

    public PtsSysServiceImpl(PtsSysDao ptsSysDao) {
        this.ptsSysDao = ptsSysDao;
    }

    @Override
    public Page<?> queryPageOrg(Integer currentPage, Integer size,String orgType) {
        Page<HashMap<String, Object>> page= PageHelper.buildPage(currentPage,size);
        List<String> orgTypes;
        if (StrUtil.isBlank(orgType)){
            orgTypes = null;
        }else{
            orgTypes = Arrays.asList(orgType.trim().split(","));
        }
        return ptsSysDao.queryPageOrg(page,orgTypes);
    }

    @Override
    public Page<?> queryPageRole(Integer currentPage, Integer size, String appId) {
        Page<HashMap<String, Object>> page= PageHelper.buildPage(currentPage,size);
        // 先查询子系统的所有角色
        Page<HashMap<String, Object>> rolePage = ptsSysDao.queryPageRole(page, appId);
        List<HashMap<String, Object>> records = rolePage.getRecords();
        List<HashMap<String, Object>> newRecords = new ArrayList<>(records.size());
        records.forEach(item -> {
            // 根据角色id，查找角色所拥有的资源
            List<HashMap<String, Object>> resourceList = ptsSysDao.queryResourceByRoleId((String) item.get("roleId"), appId);
            // 构造资源树
            List<Tree<String>> build = TreeUtil.build(resourceList, "", (map, tree) -> {
                tree.setId((String) map.get("resourceId"));
                tree.setParentId((String) map.get("parentId"));
                tree.setName((String) map.get("resourceTitle"));
                tree.put("resourceType", map.get("resourceType"));
                tree.put("path", map.get("path"));
                tree.put("permission", map.get("permission"));
            });
            // 将资源树添加到角色中
            item.put("resource", build);
            newRecords.add(item);
        });
        rolePage.setRecords(newRecords);
        return rolePage;
    }

    @Override
    public Page<?> queryPageUser(Integer currentPage, Integer size, String appId) {
        Page<HashMap<String, Object>> page= PageHelper.buildPage(currentPage,size);
        // 根据appId查询角色id列表
        List<String> roleIds = ptsSysDao.queryRoleByAppId(appId);
        // 根据角色分页查询用户列表
        Page<HashMap<String, Object>> userPage = ptsSysDao.queryPageUserByRole(page, roleIds);
        List<HashMap<String, Object>> records = userPage.getRecords();
        List<HashMap<String, Object>> newRecords = new ArrayList<>();
        records.forEach(user -> {
            // 查询每个用户的角色信息
            List<HashMap<String, Object>> roleList = ptsSysDao.queryRoleByUserId((String) user.get("userId"), appId);
            user.put("role", roleList);
            newRecords.add(user);
        });
        userPage.setRecords(newRecords);
        return userPage;
    }

    /**
     * 查询用户拥有的组织机构权限
     * @param userId 用户id
     */
    @Override
    public List<UserPermissionVo> getUserOrgPermission(String userId, String appId) {
        // 默认查当前登录的用户
        if (StrUtil.isBlank(userId)){
            userId = WebUtils.getUserId();
        }
        // 默认应用
        if (StrUtil.isBlank(appId)){
            appId = "1";
        }

        return ptsSysDao.getUserOrgPermission(userId,appId);
    }

}
