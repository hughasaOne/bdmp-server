package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.exception.EntityNotFoundException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.DictPost;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictPostService;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.system.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.system.modules.sys.common.service.UserPermissions;
import com.rhy.bdmp.system.modules.sys.dao.AppDao;
import com.rhy.bdmp.system.modules.sys.dao.OrgDao;
import com.rhy.bdmp.system.modules.sys.dao.UserDao;
import com.rhy.bdmp.system.modules.sys.domain.vo.*;
import com.rhy.bdmp.system.modules.sys.service.OrgService;
import com.rhy.bdmp.system.modules.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private OrgDao orgDao;

    @Resource
    private UserPermissions userPermissions;

    @Resource
    private AppDao appDao;

    @Resource
    public OrgService orgService;

    @Resource
    private PasswordEncoder passwordEncoder;

    //基础数据管理平台appID
    private static final String BDMPAPPID = "1";

    //数据类型
    private static final Integer DATATYPE = 1;

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    @Resource
    private IBaseDictPostService postService;

    /**
     * 查询用户拥有的组织机构权限
     *
     * @param userId 用户id
     */
    @Override
    public List<UserPermissionVo> getUserOrgPermission(String userId, String appId) {
        // 默认查当前登录的用户
        if (StrUtil.isBlank(userId)) {
            userId = WebUtils.getUserId();
        }
        // 默认应用
        if (StrUtil.isBlank(appId)) {
            appId = "1";
        }

        return userDao.getUserOrgPermission(userId, appId);
    }

    /**
     * @Description: 删除用户
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    @Transactional
    @Override
    public void delete(Set<String> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            //如果只传一个id，判断id是否存在
            if (userIds.size() == 1) {
                if (userDao.selectById(String.valueOf(userIds.toArray()[0])) == null) {
                    throw new BadRequestException("用户ID不存在");
                }
            }
            //删除用户表
            userDao.deleteBatchIds(userIds);
            // 同时删除 用户应用权限表 关联数据（根据用户id删除）
            userDao.deleteUserAppByUserId(userIds);
            // 同时删除 用户数据权限表 关联数据（根据用户id删除）
            userDao.deleteUserDataByUserIdAndDataType(userIds, DATATYPE, "");
            // 同时删除 用户角色关系表 关联数据（根据用户id删除）
            userDao.deleteUserRoleByUserId(userIds);
            // 同时删除 用户映射表 关联数据（根据用户id删除）
            userDao.deleteUserMappingByUserId(userIds);
        }
    }

    /**
     * @Description: 更新用户应用权限（根据userId更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Transactional
    @Override
    public void updateUserAppByUserId(String userId, Set<String> appIds) {
        //用户id不能为空
        if (StringUtils.isNotBlank(userId)) {
            //删除用户应用表（根据用户Id）
            userDao.deleteUserAppByUserId(Collections.singleton(userId));
            //应用id集合不能为空
            if (CollectionUtils.isNotEmpty(appIds)) {
                String currentUser = WebUtils.getUserId();
                Date currentDateTime = DateUtil.date();
                //根据用户Id添加用户应用权限
                userDao.insertUserAppByUserId(userId, appIds, currentUser, currentDateTime);
            }
        }
    }

    /**
     * @Description: 更新用户角色权限（根据userId更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Transactional
    @Override
    public void updateUseRoleByUserId(Map<String, Object> userRoleQueryMap) {
        //用户ID
        String userId = String.valueOf(userRoleQueryMap.get("userId"));
        //角色ID集合
        List<String> roleIds = (List<String>) userRoleQueryMap.get("roleIds");
        //用户ID
        String appId = String.valueOf(userRoleQueryMap.get("appId"));
        //用户ID不能为空
        if (StringUtils.isNotBlank(userId)) {
            //删除用户角色权限（根据用户ID、应用ID）
            userDao.deleteUserRoleByUserIdAndAppId(userId, appId);
            //角色id集合不能为空
            if (CollectionUtils.isNotEmpty(roleIds)) {
                String currentUser = WebUtils.getUserId();
                Date currentDateTime = DateUtil.date();
                //根据用户Id添加用户角色权限
                userDao.insertUserRoleByUserId(userId, roleIds, currentUser, currentDateTime);
            }
        }
    }

    /**
     * @Description: 查询用户应用权限（根据用户ID,返回应用id集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Override
    public List<String> findAppIdsByUserId(String userId) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)) {
            result = userDao.findAppIdsByUserId(userId);
        }
        return result;
    }

    /**
     * @Description: 查询用户角色权限（根据用户ID,返回角色ID集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Override
    public List<String> findRoleIdsByUserIdAndAppId(String userId, String appId) {
        List<String> result = new ArrayList<>();
        if (StrUtil.isNotBlank(userId) && StrUtil.isNotBlank(appId)) {
            result = userDao.findRoleIdsByUserIdAndAppId(userId, appId);
        }
        return result;
    }

    /**
     * @param appId
     * @Description: 查询用户组织关系
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Override
    public List<NodeVo> findUserOrgTree(String appId) {

        //不传应用ID的话默认appId为基础数据平台应用ID
        if (StrUtil.isBlank(appId)) {
            appId = BDMPAPPID;
        }
        List<NodeVo> result = new ArrayList<>();
        List<User> userVoList = null;
        List<Org> orgVoList = null;

        String userId = WebUtils.getUserId();
        User user = userDao.selectById(userId);
        if (null == user.getIsAdmin() || 1 != user.getIsAdmin()){
            Org org = orgDao.selectById(user.getOrgId());
            userVoList = userPermissions.getUser(org,appId);
            orgVoList = userPermissions.getOrg(org);
        }
        else {
            //查找所有用户
            userVoList = userDao.selectList(new QueryWrapper<User>()
                    .eq("datastatusid", "1")
                    .eq("app_id", appId)
                    .orderByAsc("sort")
                    .orderByDesc("create_time"));

            //查找所有组织
            orgVoList = orgDao.selectList(new QueryWrapper<Org>()
                    .orderByAsc("sort")
                    .orderByDesc("create_time"));
        }

        //用户节点放入List
        if (CollectionUtils.isNotEmpty(userVoList)) {
            for (User userVo : userVoList) {
                NodeVo node = new NodeVo();
                node.setId(userVo.getUserId());
                node.setLabel(userVo.getUsername());
                node.setValue(userVo.getUserId());
                node.setParentId(userVo.getOrgId());
                node.setNoteType("user");
                node.setMoreInfo(userVo);
                result.add(node);
            }
        }
        //组织节点放入List
        if (CollectionUtils.isNotEmpty(orgVoList)) {
            for (Org orgVo : orgVoList) {
                NodeVo node = new NodeVo();
                node.setId(orgVo.getOrgId());
                node.setLabel(orgVo.getOrgName());
                node.setValue(orgVo.getOrgId());
                node.setParentId(orgVo.getParentId());
                node.setNoteType("org");
                node.setMoreInfo(orgVo);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * @Description: 新增用户
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @Override
    public int create(User user) {
        this.passwordLegalityCheck(user);
        if (StrUtil.isNotBlank(user.getUserId())) {
            throw new BadRequestException("用户ID已存在，不能做新增操作");
        }
        // 应用ID是否存在
        if (StrUtil.isNotBlank(user.getAppId())) {
            if (appDao.selectById(user.getAppId()) == null) {
                throw new BadRequestException("应用ID不存在");
            }
        } else {
            throw new BadRequestException("应用ID不能为空");
        }
        // 组织ID是否存在
        if (StrUtil.isNotBlank(user.getOrgId())) {
            if (orgDao.selectById(user.getOrgId()) == null) {
                throw new BadRequestException("组织ID不存在");
            }
        }
        //同一个appId下用户名不能重复
        if (user.getUsername() != null) {
            List<User> users = userDao.selectList(new QueryWrapper<User>().eq("username", user.getUsername()).eq("app_id", user.getAppId()));
            if (CollectionUtils.isNotEmpty(users)) {
                throw new BadRequestException("账号已存在");
            }
        }
        //用户密码加密
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // 默认密码 Rhy@123
            user.setPassword(passwordEncoder.encode("Rhy@123"));
        }
        //默认appId为基础数据管理平台appId
        if (StrUtil.isBlank(user.getAppId())) {
            user.setAppId(BDMPAPPID);
        }
        //datastatusid默认为1
        if (user.getDatastatusid() == null) {
            user.setDatastatusid(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        user.setCreateBy(currentUser);
        user.setCreateTime(currentDateTime);
        user.setUpdateBy(currentUser);
        user.setUpdateTime(currentDateTime);
        int result = userDao.insert(user);
        return result;
    }
    /**
     * @Description: 修改用户
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @Override
    public int update(User user) {
        if (StrUtil.isBlank(user.getUserId())) {
            throw new BadRequestException("用户ID不能为空");
        } else {
            // 用户ID是否存在
            if (userDao.selectById(user.getUserId()) == null) {
                throw new BadRequestException("用户ID不存在");
            }
        }
        // 应用ID是否存在
        if (user.getAppId() != null) {
            if (appDao.selectById(user.getAppId()) == null) {
                throw new BadRequestException("应用ID不存在");
            }
        }
        // 组织ID是否存在
        if (StrUtil.isNotBlank(user.getOrgId())) {
            if (orgDao.selectById(user.getOrgId()) == null) {
                throw new BadRequestException("组织ID不存在");
            }
        }
        //同一个appId下用户名不能重复
        if (StrUtil.isNotBlank(user.getUsername())) {
            User userResult = userDao.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()).eq("app_id", user.getAppId()));
            if (userResult != null && !userResult.getUserId().equals(user.getUserId())) {
                throw new BadRequestException("用户名已存在");
            }
        }
        //不修改密码
        user.setPassword(null);
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        user.setUpdateBy(currentUser);
        user.setUpdateTime(currentDateTime);
        int result = userDao.updateById(user);
        return result;
    }

    /**
     * @Description: 修改用户配置
     * @Author: jaingzhimin
     * @Date: 2022/1/25
     */
    @Override
    public int updateUserConfig(Object userConfig) {
        String userId = WebUtils.getUserId();
        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("用户信息不存在或未登录");
        }
        User user = userDao.selectById(userId);
        JSONObject userConfigJSONObject = JSONUtil.parseObj(userConfig);
        user.setUserConfig(userConfigJSONObject.toString());
        int result = update(user);
        return result;
    }

    /**
     * @Description: 查询用户的应用数据权限（根据用户ID,返回应用ID集合）
     * @Author: dongyu
     * @Date: 2021/4/22
     */
    @Override
    public List<String> findAppPermissionIdsByUserId(String userId, String appId) {
        List<String> perssionIds = new ArrayList<>();
        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("用户ID不能为空");
        } else {
            //查询用户拥有的应用权限
            perssionIds = userDao.getUserPermission(userId, appId, 1);

        }
        return perssionIds;
    }

    /**
     * @Description: 查询用户的台账数据权限（根据用户ID,返回台账对应权限ID集合）
     * @Author: dongyu
     * @Date: 2021/4/22
     */
    @Override
    public Map<String, Object> findAssetsPermissionIdsByUserId(String userId, String appId) {
        Map<String, Object> result = new HashMap<>();
        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("用户ID不能为空");
        } else {
            Integer dataType = 2;
            //获取用户的权限级别
            User user = userDao.selectById(userId);
            if (user == null) {
                throw new BadRequestException("用户ID不存在");
            }
            //权限级别
            Integer dataPermissionsLevel = user.getDataPermissionsLevel();
            //获取用户数据权限表权限id
            List<String> perssionIds = userDao.getUserPermission(userId, appId, 2);
            result.put("dataPermissionsLevel", dataPermissionsLevel);
            result.put("data", perssionIds);
        }
        return result;
    }

    /**
     * @param sysUserDataQueryVo
     * @Description: 更新用户的应用数据权限（根据用户ID、应用ID）
     * @Author: dongyu
     * @Date: 2021/4/22
     */
    @Transactional
    @Override
    public void updateAppPermission(SysUserDataQueryVo sysUserDataQueryVo) {
        String userId = sysUserDataQueryVo.getUserId();
        Set<String> permissionsIds = sysUserDataQueryVo.getPermissionsIds();
        String appId = sysUserDataQueryVo.getAppId();

        //用户id不能为空
        if (StringUtils.isNotBlank(userId)) {
            Integer dataType = 1;
            //删除用户数据权限（根据用户id、dataType）
            userDao.deleteUserDataByUserIdAndDataType(Collections.singleton(userId), dataType, appId);
            //权限id集合不为空
            if (CollectionUtils.isNotEmpty(permissionsIds)) {
                String createBy = WebUtils.getUserId();
                Date createTime = DateUtil.date();
                //根据用户id添加用户数据权限
                userDao.insertUserDataByUserId(userId, dataType, permissionsIds, createBy, createTime, appId);
            }
        } else {
            throw new BadRequestException("用户ID不能为空");
        }
    }

    /**
     * @Description: 更新用户的台账数据权限（根据用户ID、应用ID、权限级别）
     * @Author: dongyu
     * @Date: 2021/4/22
     */
    @Transactional
    @Override
    public void updateAssetsPermission(AssetsUserDataQueryVo assetsUserDataQueryVo) {
        String userId = assetsUserDataQueryVo.getUserId();
        Set<String> permissionsIds = assetsUserDataQueryVo.getPermissionsIds();
        Integer permissionsLevel = assetsUserDataQueryVo.getPermissionsLevel();
        String appId = assetsUserDataQueryVo.getAppId();
        //用户id不能为空
        if (StringUtils.isNotBlank(userId)) {
            //数据权限级别不能为空
            if (permissionsLevel == null) {
                throw new BadRequestException("数据权限级别不能为空");
            } else if (permissionsLevel != 1 && permissionsLevel != 2 && permissionsLevel != 3) {
                throw new BadRequestException("数据权限级别不存在");
            }
            //查询用户信息
            User user = userDao.selectById(userId);
            if (user == null) {
                throw new BadRequestException("用户ID不存在");
            }
            Integer dataType = 2;
            //删除用户数据权限（根据用户id、dataType）
            userDao.deleteUserDataByUserIdAndDataType(Collections.singleton(userId), dataType, appId);
            //权限id集合不能为空
            if (CollectionUtils.isNotEmpty(permissionsIds)) {
                String createBy = WebUtils.getUserId();
                Date createTime = DateUtil.date();
                //根据用户id添加用户数据权限
                userDao.insertUserDataByUserId(userId, dataType, permissionsIds, createBy, createTime, appId);
            }
            //更新用户表权限级别
            String currentUser = WebUtils.getUserId();
            Date currentDateTime = DateUtil.date();
            User updateUser = new User();
            updateUser.setUserId(userId);
            updateUser.setDataPermissionsLevel(permissionsLevel);
            updateUser.setUpdateBy(currentUser);
            updateUser.setUpdateTime(currentDateTime);
            userDao.updateById(updateUser);
        } else {
            throw new BadRequestException("用户ID不能为空");
        }
    }

    /**
     * 更新用户的组织机构权限
     */
    @Override
    public void updateUserOrgPermission(SysUserDataQueryVo sysUserDataQueryVo) {
        String userId = sysUserDataQueryVo.getUserId();
        Set<String> permissionsIds = sysUserDataQueryVo.getPermissionsIds();
        String appId = sysUserDataQueryVo.getAppId();

        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("用户ID不为空");
        }

        Integer dataType = 3;

        if (permissionsIds.isEmpty()) {
            // 删除所有权限
            List<Org> orgs = orgDao.selectList(null);
            for (Org org : orgs) {
                permissionsIds.add(org.getOrgId());
            }
            //删除用户数据权限（根据用户id、dataType）
            userDao.deleteUserDataByUserIdAndDataType(Collections.singleton(userId), dataType, appId);
        } else {
            if (StrUtil.isBlank(appId)) {
                appId = appDao.selectOne(new QueryWrapper<App>().eq("app_name", "基础数据管理平台")).getAppId();
            }

            //删除用户数据权限（根据用户id、dataType）
            userDao.deleteUserDataByUserIdAndDataType(Collections.singleton(userId), dataType, appId);

            // 更新用户权限级别
            String createBy = WebUtils.getUserId();
            Date createTime = DateUtil.date();

            //根据用户id添加用户数据权限
            userDao.insertUserDataByUserId(userId, dataType, permissionsIds, createBy, createTime, appId);
        }
    }

    /**
     * 修改密码
     *
     * @param userPassVo
     * @return
     */
    @Override
    public int updatePass(UserPassVo userPassVo) throws Exception {
        if (!StrUtil.isNotBlank(userPassVo.getUserId())) {
            throw new BadRequestException("用户ID不能为空");
        }
        User userDb = userDao.selectOne(new QueryWrapper<User>().eq("user_id", userPassVo.getUserId()));
        if (null == userDb) {
            throw new EntityNotFoundException(User.class, "user_id", userPassVo.getUserId());
        }
//        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, userPassVo.getOldPass());
//        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, userPassVo.getNewPass());
        if (!passwordEncoder.matches(userPassVo.getOldPass(), userDb.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(userPassVo.getNewPass(), userDb.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userDb.setPassword(passwordEncoder.encode(userPassVo.getNewPass()));
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        userDb.setUpdateBy(currentUser);
        userDb.setUpdateTime(currentDateTime);
        int result = userDao.updateById(userDb);
        return result;
    }

    /**
     * 用户列表查询
     *
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public Object list(QueryVO queryVO) {
        // todo 应急改动： 解决不是管理员，能看到所有用户的问题
        String userId = WebUtils.getUserId();
        User user = userDao.selectById(userId);
        QueryVO.QueryCondition[] queryConditionArray = queryVO.getQueryConditionArray();
        boolean isAll = false;
        if (null != queryConditionArray && queryConditionArray.length > 0){
            Page<User> resPage = new Page<>();
            for (QueryVO.QueryCondition queryCondition : queryConditionArray) {
                String columnName = queryCondition.getColumnName();
                if (StrUtil.equals(columnName,"org_id")){
                    List<String> values = queryCondition.getValues();
                    for (String value : values) {
                        if (StrUtil.equals(value,"all") && (null == user.getIsAdmin() || 1 != user.getIsAdmin())){
                            // 不是管理员又要查全部，则查询本身及以下
                            isAll = true;
                        }
                    }
                }
                if (isAll){
                    queryCondition.setColumnName("org_id");
                    queryCondition.setQueryType("in");

                    List<Org> parents = new ArrayList<>();
                    Org org = orgDao.selectById(user.getOrgId());
                    if (null == org){
                        throw new BadRequestException("用户所属组织不存在");
                    }
                    List<Org> temp = userPermissions.getOrg(org);

                    List<String> orgIds = temp.stream().map(orgTemp -> orgTemp.getOrgId()).collect(Collectors.toList());
                    queryCondition.setValues(orgIds);

                    Query<User> query = new Query<>(queryVO);
                    Page<User> page = query.getPage();
                    QueryWrapper<User> queryWrapper = query.getQueryWrapper();
                    resPage = userDao.selectPage(page,queryWrapper);
                    break;
                }
            }
            if (isAll){
                return resPage;
            }
            else {
                for (QueryVO.QueryCondition queryCondition : queryConditionArray) {
                    String columnName = queryCondition.getColumnName();
                    if (StrUtil.equals(columnName,"org_id")){
                        List<String> values = queryCondition.getValues();
                        for (String value : values) {
                            if (StrUtil.equals(value,"all")){
                                // 不是管理员又要查全部，则查询本身及以下
                                queryCondition.setValues(new ArrayList());
                            }
                        }
                    }
                }
            }
        }

        //1、有查询条件
        if (null != queryVO) {
            Query<User> query = new Query<>(queryVO);
            Page<User> page = query.getPage();
            QueryWrapper<User> queryWrapper = query.getQueryWrapper();
            Map<String, Object> paramMap = queryVO.getParamsMap();
            if (MapUtil.isNotEmpty(paramMap) && StrUtil.isNotBlank(MapUtil.getStr(paramMap, "orgId"))) {
                String orgId = MapUtil.getStr(paramMap, "orgId");
                if ("other".equals(orgId)) {
                    queryWrapper.and(wrapper -> wrapper.eq("org_id", "").or().isNull("org_id"));
                } else {
                    Set<String> orgIds = orgService.getOrgIdsByDown(orgId, true);
                    queryWrapper.lambda().in(User::getOrgId, orgIds);
                }
            }
            queryWrapper.orderByAsc("sort").orderByDesc("create_time");
            if (null != page) {
                page = userDao.selectPage(page, queryWrapper);
                //获取User
                List<User> userList = page.getRecords();
                //User转换成UserVo
                List<UserVo> userVoList = convertVo(userList);
                Page<UserVo> userVoPage = new Page<>();
                //User的分页信息复制到UserVo的分页信息
                BeanUtils.copyProperties(page, userVoPage);
                userVoPage.setRecords(userVoList);
                return userVoPage;
            }
            List<User> entityList = userDao.selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(userDao.selectList(new QueryWrapper<User>().orderByAsc("sort").orderByDesc("create_time")));
    }

    /**
     * 查看用户(根据ID)
     *
     * @param userId
     * @return
     */
    @Override
    public UserVo detail(String userId) {
        if (!StrUtil.isNotBlank(userId)) {
            throw new BadRequestException("用户ID不能为空");
        }
        User user = userDao.selectById(userId);
        if (user != null) {
            List<User> users = new ArrayList<>();
            users.add(user);
            return convertVo(users).get(0);
        }
        return null;
    }

    /**
     * User转换成UserVo
     *
     * @param userList
     * @return
     */
    public List<UserVo> convertVo(List<User> userList) {
        //组织列表
        List<Org> orgs = orgDao.selectList(new QueryWrapper<Org>());
        List<DictPost> postList = postService.list(new QueryWrapper<DictPost>());
        //用户Vo列表
        List<UserVo> userVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (User user : userList) {
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(user, userVo);
                if (CollectionUtils.isNotEmpty(orgs)) {
                    //上级组织名称
                    orgs.forEach(o -> {
                        if (o.getOrgId().equals(user.getOrgId())) {
                            userVo.setOrgName(o.getOrgName());
                            userVo.setOrgType(o.getOrgType());
                        }
                    });
                }
                if (CollUtil.isNotEmpty(postList)){
                    for (DictPost dictPost : postList) {
                        if (StrUtil.isNotBlank(userVo.getPost())){
                            if (userVo.getPost().equals(dictPost.getPostId())){
                                userVo.setPostName(dictPost.getName());
                                break;
                            }
                        }
                    }
                }
                userVoList.add(userVo);
            }
        }
        return userVoList;
    }

    /**
     * @Description: 重置密码
     * @Author: dongyu
     * @Date: 2021/4/27
     */
    @Override
    public int resetPassword(String userId, String newPass) throws Exception {
        if (!StrUtil.isNotBlank(userId)) {
            throw new BadRequestException("用户ID不能为空");
        }
        User userDb = userDao.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        if (null == userDb) {
            throw new EntityNotFoundException(User.class, "user_id", userId);
        }
//        String Pass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, newPass);
        userDb.setPassword(passwordEncoder.encode(newPass));
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        userDb.setUpdateBy(currentUser);
        userDb.setUpdateTime(currentDateTime);
        userDb.setPwdResetTime(currentDateTime);
        int result = userDao.updateById(userDb);
        return result;
    }

    @Override
    public List<String> findRoleIdsByUserId(String userId) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)) {
            result = userDao.findRoleIdsByUserId(userId);
        }
        return result;
    }

    /**
     * @param isUseUserPermissions
     * @Description: 查询当前用户的应用权限
     * @Author: dongyu
     * @Date: 2021/5/10
     */
    @Override
    public List<App> findCurrentUserApp(Boolean isUseUserPermissions) {
        List<App> result = new ArrayList<>();
        //查询当前用户Id
        String userId = WebUtils.getUserId();
        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("未获取到当前用户");
        }
        // 获取当前用户信息
        User user = userDao.selectById(userId);
        //判断用户是否为admin
        if (null != user.getIsAdmin() && user.getIsAdmin() == 1) {
            isUseUserPermissions = false;
        }
        //isUseUserPermissions等于true时按权限查找,isUseUserPermissions等于false查所有
        result = userDao.findAppByUser(isUseUserPermissions, userId);
        return result;
    }

    /**
     * 密码合法性校验
     * 1）口令长度至少8个字符，最多16；
     * 2）口令必须包含如下至少三种字符的组合:
     *       －至少一个小写字母；
     *       －至少一个大写字母；
     *       －至少一个数字；
     *       －至少一个特殊字符
     */
    private void passwordLegalityCheck(User user) {
        String password = user.getPassword();
        if (StrUtil.isBlank(password)){
            throw new BadRequestException("密码不为空");
        }
        String expression = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,16}$";
        boolean matches = password.matches(expression);
        if (!matches){
            throw new BadRequestException("密码必须包含数字、小写字母、大写字母、特殊字符等任意3类组合，且长度设置为8至16位");
        }
    }
}
