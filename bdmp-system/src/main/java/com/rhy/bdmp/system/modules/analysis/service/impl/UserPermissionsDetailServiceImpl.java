package com.rhy.bdmp.system.modules.analysis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserService;
import com.rhy.bdmp.system.modules.analysis.dao.UserPermissionsDetailDao;
import com.rhy.bdmp.system.modules.analysis.domain.vo.AuthorizationVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.ExcelUserPermissionsVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.OrgUserVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.UserPermissionsVO;
import com.rhy.bdmp.system.modules.analysis.service.UserPermissionsDetailService;
import com.rhy.bdmp.system.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.system.modules.sys.common.service.UserPermissions;
import com.rhy.bdmp.system.modules.sys.domain.vo.OrgVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.UserVo;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户权限明细业务层
 *
 * @author 庞盛权
 * @Date 2022/05/27
 */
@Service
public class UserPermissionsDetailServiceImpl implements UserPermissionsDetailService {

    @Resource
    private UserPermissionsDetailDao userPermissionsDetailDao;

    @Resource
    private IBaseUserService userService;

    @Resource
    private IBaseOrgService orgService;

    @Resource
    private UserPermissions userPermissions;

    private static final String PERMISSIONS_MENU = "menu";
    private static final String PERMISSIONS_APP_VISIT = "app";
    private static final String PERMISSIONS_APP_DATA = "appData";
    private static final String PERMISSIONS_DATA = "data";
    private static final String PERMISSIONS_ALL = "all";

    /**
     * 根据运营公司查询用户权限明细导出Excel
     *
     * @param orgId 机构id
     * @return 权限明细Vo集合
     */
    @Override
    public List<ExcelUserPermissionsVO> getCompanyUserPermissions(String orgId) {
        List<ExcelUserPermissionsVO> list = new ArrayList<>();
        // 公司直属用户
        List<UserVo> vos = userPermissionsDetailDao.getOrgUser(orgId);
        // 部门用户
        List<UserVo> companyUserRecursion = userPermissionsDetailDao.getCompanyUserRecursion(orgId);
        vos.addAll(companyUserRecursion.stream().filter(x -> StrUtil.isNotEmpty(x.getUserId())).collect(Collectors.toList()));
        // 递归查询子部门用户
        List<String> orgIds = companyUserRecursion.stream().map(UserVo::getOrgId).distinct().collect(Collectors.toList());
        selectOrgUserRecursion(vos, orgIds);
        for (String userId : vos.stream().map(UserVo::getUserId).collect(Collectors.toList())) {
            if (StrUtil.isBlank(userId)) {
                continue;
            }
            ExcelUserPermissionsVO excelVo = new ExcelUserPermissionsVO();
            // 查询用户权限
            UserPermissionsVO userPermissionsDetail = getUserPermissionsDetail(userId, Boolean.TRUE);
            excelVo.setUsername(userPermissionsDetail.getUsername());
            excelVo.setNickName(userPermissionsDetail.getNickName());
            excelVo.setOrgName(userPermissionsDetail.getOrgName());
            excelVo.setDataPermissionsLevel(dataPermissionsLevel(userPermissionsDetail.getDataPermissionsLevel()));
            excelVo.setMenuName(listNonNullToString(userPermissionsDetail.getMenuName()));
            excelVo.setAppName(listNonNullToString(userPermissionsDetail.getAppName()));
            excelVo.setAppDataName(listNonNullToString(userPermissionsDetail.getAppDataName()));
            excelVo.setDataName(listNonNullToString(userPermissionsDetail.getDataName()));
            excelVo.setDepartmentName(listNonNullToString(userPermissionsDetail.getDepartmentName()));
            list.add(excelVo);
        }
        return list;
    }


    /**
     * 根据用户id查询用户权限明细
     *
     * @param userId  用户id
     * @param isExcel 数据是否作用于导出Excel
     * @return 权限明细VO
     */
    @Override
    public UserPermissionsVO getUserPermissionsDetail(String userId, Boolean isExcel) {
        UserPermissionsVO permissionsVO = new UserPermissionsVO();
        List<UserPermissionsVO> list;
        if (StrUtil.isEmpty(userId)) {
            return permissionsVO;
        }
        // 用户信息
        permissionsVO = userPermissionsDetailDao.getUser(userId);
        // 菜单权限 查出拥有的菜单id和菜单所属的AppName
        list = userPermissionsDetailDao.getUserMenu(userId);
        if (CollUtil.isNotEmpty(list)) {
            // 导出excel时需要拼接名称
            if (isExcel) {
                list.forEach(vo -> {
                    com.rhy.bdmp.base.modules.sys.domain.po.Resource resource = userPermissionsDetailDao.getResourceById(vo.getValueId());
                    // 拼接AppName+菜单name
                    vo.setValueName(
                            StrUtil.equals(vo.getValueName(), recursion(resource).getResourceTitle())
                                    ? vo.getValueName()
                                    : vo.getValueName() + "/" + recursion(resource).getResourceTitle());
                });
                permissionsVO.setMenuId(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueId).collect(Collectors.toList()));
                permissionsVO.setMenuName(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueName).collect(Collectors.toList()));
            } else {
                // 页面显示通过appName分组
                Map<String, List<String>> collect = list.stream()
                        .collect(Collectors.groupingBy(UserPermissionsVO::getValueName, Collectors.mapping(UserPermissionsVO::getTempValueName, Collectors.toList())));
                permissionsVO.setAppGroupMenu(collect);
            }
        }
        // 应用访问权限
        list = userPermissionsDetailDao.getUserIdApp(userId);
        if (CollUtil.isNotEmpty(list)) {
            permissionsVO.setAppName(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueName).collect(Collectors.toList()));
        }
        // 部门权限,只在导出Excel时使用
        if (isExcel) {
            String orgId = permissionsVO.getOrgId();
            List<OrgVo> orgVos = userPermissionsDetailDao.getUserIdOrg(orgId);
            if (CollUtil.isNotEmpty(orgVos)) {
                orgVos.forEach(vo -> {
                    vo.setOrgShortName(recursion(vo, orgId).getOrgShortName());
                });
                permissionsVO.setDepartmentName(orgVos.stream()
                        .filter(Objects::nonNull)
                        .map(OrgVo::getOrgShortName)
                        .collect(Collectors.toList()));
            }
        }
        // 应用数据权限
        List<String> appList = userPermissionsDetailDao.getCompanyUserIdAppData(userId);
        if (CollUtil.isNotEmpty(appList)) {
            permissionsVO.setAppDataName(appList.stream().filter(StrUtil::isNotEmpty).collect(Collectors.toList()));
        }
        // 台账数据权限
        getDataPermissions(permissionsVO, list, userId, isExcel);
        return permissionsVO;
    }

    /**
     * 获取台账权限,消除方法过长
     *
     * @param permissionsVO 权限对象
     * @param list          权限对象
     * @param userId        用户id
     * @param isExcel       是否为导出Excel
     */
    private void getDataPermissions(UserPermissionsVO permissionsVO, List<UserPermissionsVO> list, String userId, Boolean isExcel) {
        String dataPermissionsLevel = permissionsVO.getDataPermissionsLevel();
        if (StrUtil.isNotEmpty(dataPermissionsLevel)) {
            if (StrUtil.equals(PermissionsEnum.ORG.getCode(), dataPermissionsLevel)) {
                list = userPermissionsDetailDao.getUserDataPermissions(userId, PermissionsEnum.ORG.getCode());
                if (CollUtil.isNotEmpty(list)) {
                    permissionsVO.setDataId(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueId).collect(Collectors.toList()));
                    permissionsVO.setDataName(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueName).collect(Collectors.toList()));
                }
            }
            if (StrUtil.equals(PermissionsEnum.WAY.getCode(), dataPermissionsLevel)) {
                list = userPermissionsDetailDao.getUserDataPermissions(userId, PermissionsEnum.WAY.getCode());
                setPermissionsVo(permissionsVO, list, isExcel);
            }
            if (StrUtil.equals(PermissionsEnum.FAC.getCode(), dataPermissionsLevel)) {
                list = userPermissionsDetailDao.getUserDataPermissions(userId, PermissionsEnum.FAC.getCode());
                setPermissionsVo(permissionsVO, list, isExcel);
            }
        }
    }

    /**
     * 重复代码提取,查询结果插入至权限对象中
     *
     * @param permissionsVO 权限对象
     * @param list          权限对象
     * @param isExcel       是否为导出Excel
     */
    private void setPermissionsVo(UserPermissionsVO permissionsVO, List<UserPermissionsVO> list, Boolean isExcel) {
        if (CollUtil.isNotEmpty(list)) {
            if (isExcel) {
                permissionsVO.setDataId(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueId).collect(Collectors.toList()));
                permissionsVO.setDataName(list.stream().filter(Objects::nonNull).map(UserPermissionsVO::getValueName).collect(Collectors.toList()));
            } else {
                Map<String, List<String>> collect = list.stream().filter(Objects::nonNull)
                        .collect(Collectors.groupingBy(UserPermissionsVO::getTempValueName, Collectors.mapping(UserPermissionsVO::getValueName, Collectors.toList())));
                permissionsVO.setOrgGroupData(collect);
            }
        }
    }

    /**
     * 一键同步授权
     *
     * @param authorizationVo 同步权限vo
     * @return bool 执行结果
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, BadSqlGrammarException.class, Exception.class})
    public Boolean oneClickAuthorization(AuthorizationVO authorizationVo) {
        // 识别授权权限
        String permissionsType = authorizationVo.getPermissionsType();
        List<String> userIds = authorizationVo.getUserIds();
        String permissionsId = String.valueOf(authorizationVo.getPermissionsUserId());
        if (CollUtil.isEmpty(userIds) || StrUtil.isBlank(permissionsId) || userIds.contains(permissionsId)) {
            return Boolean.FALSE;
        }
        String createBy = WebUtils.getUserId();
        try {
            userIds.forEach(userId -> {
                // 菜单权限
                if (StrUtil.equals(permissionsType, PERMISSIONS_MENU) || StrUtil.equals(permissionsType, PERMISSIONS_ALL)) {
                    userPermissionsDetailDao.deleteUserRole(userId);
                    userPermissionsDetailDao.setUserRolePermissions(permissionsId, userId, createBy);
                }
                // 应用访问权限
                if (StrUtil.equals(permissionsType, PERMISSIONS_APP_VISIT) || StrUtil.equals(permissionsType, PERMISSIONS_ALL)) {
                    userPermissionsDetailDao.deleteUserApp(userId);
                    userPermissionsDetailDao.setUserAppPermissions(permissionsId, userId, createBy);
                }
                // 应用数据权限
                if (StrUtil.equals(permissionsType, PERMISSIONS_APP_DATA) || StrUtil.equals(permissionsType, PERMISSIONS_ALL)) {
                    userPermissionsDetailDao.deleteUserAppData(userId, "1");
                    userPermissionsDetailDao.setUserDataPermissions(permissionsId, userId, createBy, "1");
                }
                // 台账数据权限
                if (StrUtil.equals(permissionsType, PERMISSIONS_DATA) || StrUtil.equals(permissionsType, PERMISSIONS_ALL)) {
                    userPermissionsDetailDao.deleteUserAppData(userId, "2");
                    userPermissionsDetailDao.setUserDataPermissions(permissionsId, userId, createBy, "2");
                }
            });
            return Boolean.TRUE;
        } catch (Exception e) {
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Boolean.FALSE;
        }
    }

    /**
     * @author weicaifu
     */
    @Override
    public Object selectOrgUser() {
        String userId = WebUtils.getUserId();
        User user = userService.getById(userId);

        List<OrgUserVO> orgUserList = new ArrayList<>();
        if (null == user.getIsAdmin() || 1 != user.getIsAdmin()){
            // 获取该用户能看到的组织和用户范围
            Org org = orgService.getById(user.getOrgId());
            List<Org> orgList = userPermissions.getOrg(org);
            List<User> userList = userPermissions.getUser(org,"");

            if (CollUtil.isEmpty(orgList)){
                return null;
            }

            for (Org orgTemp : orgList) {
                OrgUserVO orgUserVO = new OrgUserVO();
                orgUserVO.setIsUser(false);
                orgUserVO.setId(orgTemp.getOrgId());
                orgUserVO.setLabel(orgTemp.getOrgShortName());
                orgUserVO.setSort(Optional.ofNullable(orgTemp.getSort()).orElse(0L).intValue());
                orgUserVO.setPid(orgTemp.getParentId());
                orgUserList.add(orgUserVO);
            }

            for (User userTemp : userList) {
                OrgUserVO orgUserVO = new OrgUserVO();
                orgUserVO.setIsUser(true);
                orgUserVO.setId(userTemp.getUserId());
                orgUserVO.setLabel(userTemp.getNickName());
                orgUserVO.setSort(Optional.ofNullable(userTemp.getSort()).orElse(0L).intValue());
                orgUserVO.setPid(userTemp.getOrgId());
                orgUserList.add(orgUserVO);
            }
        }
        else {
            orgUserList = userPermissionsDetailDao.getNodeList();
        }

        if (CollUtil.isEmpty(orgUserList)){
            return null;
        }

        List<TreeNode<String>> nodeList = new ArrayList<>();

        for (OrgUserVO orgUserVO : orgUserList) {
            nodeList.add(new TreeNode<String>()
                    .setId(orgUserVO.getId())
                    .setName(orgUserVO.getLabel())
                    .setParentId(orgUserVO.getPid())
                    .setWeight(orgUserVO.getSort())
                    .setExtra(JSONUtil.createObj()
                            .putOnce("isUser", orgUserVO.getIsUser())
                            )
            );
        }

        // 构造树结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("pid");
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * 查询组织公司机构用户
     *
     * @return 返回运营公司，组织机构和用户呈现树形数据结构
     */
    /*@Override
    public List<OrgUserVO> selectOrgUser() {
        Set<OrgUserVO> orgUserVoS = userPermissionsDetailDao.selectOrgAll();
        Set<OrgUserVO> set = new HashSet<>(orgUserVoS);
        orgUserVoS.forEach(orgUserVO -> {
            set.addAll(userPermissionsDetailDao.selectOrg1(orgUserVO.getId()));
        });
        Set<String> collect = set.stream().map(OrgUserVO::getId).collect(Collectors.toSet());
        set.addAll(userPermissionsDetailDao.selectOrgUser(collect));
        return set.stream()
                // 过滤父节点
                .filter(user -> !collect.contains(user.getPid()))
                // 递归子节点
                .peek(user -> user.setChildren(getChildrens(user, set)))
                // 父节点排序
                .sorted(Comparator.comparing(orgUserVO -> orgUserVO.getSort() == null ? 0 : orgUserVO.getSort()))
                .collect(Collectors.toList());
    }*/

    /**
     * 查询组织机构
     *
     * @return 返回组织机构 基本集合数据结构
     */
    @Override
    public List<OrgUserVO> selectOrg() {
        return userPermissionsDetailDao.selectOrg();
    }

    /**
     * 递归生成有序节点树
     *
     * @param orgUser     部门用户对象
     * @param orgUserList 部门用户集合
     * @return 部门vo 有序节点树数据结构
     */
    private List<OrgUserVO> getChildrens(OrgUserVO orgUser, Set<OrgUserVO> orgUserList) {
        return orgUserList.stream()
                // 过滤子节点
                .filter(orgUserVO -> StrUtil.equals(orgUserVO.getPid(), orgUser.getId()))
                // 递归
                .peek(orgUserVO -> orgUserVO.setChildren(getChildrens(orgUserVO, orgUserList)))
                // 子节点排序
                .sorted(Comparator.comparing(orgUserVO -> orgUserVO.getSort() == null ? 0 : orgUserVO.getSort()))
                .collect(Collectors.toList());
    }


    /**
     * 递归查询部门
     *
     * @param orgVo 部门vo
     * @return 部门vo
     */
    private OrgVo recursion(OrgVo orgVo, String orgId) {
        if (StrUtil.isNotEmpty(orgVo.getParentId()) && !"0".equals(orgVo.getParentId()) && !"-1".equals(orgVo.getParentId())) {
            OrgVo orgVo1 = userPermissionsDetailDao.getOrgById(orgVo.getParentId());
            orgVo1.setOrgShortName(orgVo1.getOrgShortName() + "/" + orgVo.getOrgShortName());
            if (StrUtil.equals(orgId, orgVo.getParentId())) {
                return orgVo1;
            }
            return recursion(orgVo1, orgId);
        }
        return orgVo;
    }


    /**
     * 递归查询菜单
     *
     * @param resource 资源vo
     * @return 资源vo
     */
    private com.rhy.bdmp.base.modules.sys.domain.po.Resource recursion(com.rhy.bdmp.base.modules.sys.domain.po.Resource resource) {
        if (StrUtil.isNotEmpty(resource.getParentId()) && !"0".equals(resource.getParentId()) && !"-1".equals(resource.getParentId())) {
            com.rhy.bdmp.base.modules.sys.domain.po.Resource resource1 = userPermissionsDetailDao.getResourceById(resource.getParentId());
            resource1.setResourceTitle(resource1.getResourceTitle() + "/" + resource.getResourceTitle());
            return recursion(resource1);
        }
        return resource;
    }

    /**
     * 递归查询部门用户，维护排序
     * new LinkedHashSet(collection)
     *
     * @param userVOList 用户集合
     * @param orgIds     递归参数
     */
    private void selectOrgUserRecursion(List<UserVo> userVOList, List<String> orgIds) {
        if (CollUtil.isNotEmpty(orgIds)) {
            List<UserVo> list;
            List<String> test = new ArrayList<>();
            for (String orgId : orgIds) {
                list = userPermissionsDetailDao.getCompanyUserRecursion(orgId);
                userVOList.addAll(list.stream().filter(x -> StrUtil.isNotEmpty(x.getUserId())).collect(Collectors.toList()));
                test.addAll(list.stream().map(UserVo::getOrgId).distinct().collect(Collectors.toList()));
            }
            selectOrgUserRecursion(userVOList, test);
        }
    }


    /**
     * 清除List中null与空字符并转换成逗号分割String
     *
     * @param list String对象集合
     * @return List转换成以“,”切割String
     */
    private String listNonNullToString(List<String> list) {
        return CollUtil.isEmpty(list)
                ? ""
                : CollUtil.join(list.stream().filter(StrUtil::isNotEmpty).collect(Collectors.toList()), ",");
    }

    /**
     * 修改权限级别为名称
     *
     * @param level 权限级别
     * @return 权限级别中文名称
     */
    private String dataPermissionsLevel(String level) {
        if (StrUtil.isNotEmpty(level)) {
            if (StrUtil.equals(level, PermissionsEnum.ORG.getCode())) {
                return PermissionsEnum.ORG.getName();
            }
            if (StrUtil.equals(level, PermissionsEnum.WAY.getCode())) {
                return PermissionsEnum.WAY.getName();
            }
            if (StrUtil.equals(level, PermissionsEnum.FAC.getCode())) {
                return PermissionsEnum.FAC.getName();
            }
        }
        return "";
    }
}
