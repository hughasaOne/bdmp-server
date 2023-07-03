package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.system.modules.sys.dao.*;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppTypeVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppVo;
import com.rhy.bdmp.system.modules.sys.service.AppService;
import com.rhy.bdmp.system.modules.sys.service.AppTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppDao appDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ResourceDao resourceDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private AppTypeDao appTypeDao;

    @Resource
    private AppTypeService appTypeService;

    //数据类型
    private static final Integer DATATYPE = 1;

    /**
     * 删除应用信息
     * @param appIds
     * @return
     */
    @Transactional
    @Override
    public void delete(Set<String> appIds) {
        if (CollectionUtils.isNotEmpty(appIds)) {
            //如果只传一个id，判断id是否存在
            if (appIds.size() == 1) {
                if (appDao.selectById(String.valueOf(appIds.toArray()[0])) == null) {
                    throw new BadRequestException("应用ID不存在");
                }
            }
            //判断app是否有关联的资源
            List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resouceList = resourceDao.selectList(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>().in("app_id", appIds));
            if (CollectionUtils.isNotEmpty(resouceList)) {
                throw new BadRequestException("应用存在关联资源");
            }
            //判断app是否有关联的角色
            List<Role> roleVoList = roleDao.selectList(new QueryWrapper<Role>().in("app_id", appIds));
            if (CollectionUtils.isNotEmpty(roleVoList)) {
                throw new BadRequestException("应用存在关联角色");
            }
            //删除用户应用权限表（根据应用id）
            appDao.deleteUserAppByAppId(appIds);
            //删除用户映射表（根据应用id）
            appDao.deleteUserMappingByAppId(appIds);
            //删除应用
            appDao.deleteBatchIds(appIds);
        }
    }

    /**
     * @Description: 查询应用类型与应用关系
     * @Author: dongyu
     * @Date: 2021/4/15
     * @param isUseUserPermissions
     */
    @Override
    public List<NodeVo> findAppTypeAndAppTree(Boolean isUseUserPermissions) {
        List<NodeVo> result = new ArrayList<>();
        //获取当前用户id
        String userId = WebUtils.getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new BadRequestException("未获取到当前用户");
        }
        // 获取当前用户信息
        User user = userDao.selectById(userId);
        //判断用户是否为admin
        if (null != user.getIsAdmin() && user.getIsAdmin() == 1) {
            isUseUserPermissions = false;
        }
        //应用id集合
        List<String> appIds = new ArrayList<>();
        //应用集合
        List<App> appList = new ArrayList<>();
        //isUseUserPermissions等于true时按权限查找,isUseUserPermissions等于false查所有
        if (isUseUserPermissions) {
            //获取应用Ids（用户数据权限，根据用户id查询,dataType=1）
            appIds = userDao.selectPermissionIds(userId, DATATYPE);
            //判断应用权限为空抛出异常
            if (CollectionUtils.isEmpty(appIds)) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (CollectionUtils.isNotEmpty(appIds)) {
                //查找应用（根据应用id）
                appList = appDao.selectList(new QueryWrapper<App>().in("app_id", appIds).eq("datastatusid", "1").orderByAsc("sort").orderByDesc("create_time"));
                if (CollectionUtils.isNotEmpty(appList)) {
                    //应用类别id集合
                    List<String> appTypeIds = appList.stream().map(App::getAppTypeId).distinct().collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(appTypeIds)) {
                        //应用类别节点集合
                        List<AppTypeVo> appTypeNodeList = new ArrayList<>();
                        //查找所有应用类别及其父类别（根据应用类别id）
                        for (String appTypeId : appTypeIds) {
                            //获取应用类别及其父类别
                            List<AppTypeVo> appTypeVos = appTypeDao.findAppTypesParent(appTypeId);
                            appTypeNodeList.addAll(appTypeVos);
                        }

                        //去重
                        List<AppTypeVo> distinctAppType = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(appTypeNodeList)) {
                            distinctAppType = appTypeNodeList.stream().distinct().collect(Collectors.toList());
                        }
/*                        //按照sort升序、create_time降序
                        distinctAppType.sort(Comparator.comparing(AppTypeVo::getSort).thenComparing(AppTypeVo::getCreateTime, Comparator.reverseOrder()));*/
                        //构造应用类别树节点
                        for (AppTypeVo appTypeVo : distinctAppType) {
                            NodeVo nodeVo = new NodeVo();
                            nodeVo.setId(appTypeVo.getAppTypeId());
                            nodeVo.setLabel(appTypeVo.getAppTypeName());
                            nodeVo.setValue(appTypeVo.getAppTypeId());
                            nodeVo.setParentId(appTypeVo.getParentId());
                            nodeVo.setNoteType("appType");
                            nodeVo.setMoreInfo(appTypeVo);
                            result.add(nodeVo);
                        }
                    }
                }
            }
        } else {
            //所有应用
            appList = appDao.selectList(new QueryWrapper<App>().eq("datastatusid", "1").orderByAsc("sort").orderByDesc("create_time"));
            //所有应用类别
            List<AppType> appTypes = appTypeDao.selectList(new QueryWrapper<AppType>().orderByAsc("sort").orderByDesc("create_time"));
            if (CollectionUtils.isNotEmpty(appTypes)) {
                //构造应用类别树节点
                for (AppType appTypeVo : appTypes) {
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setId(appTypeVo.getAppTypeId());
                    nodeVo.setLabel(appTypeVo.getAppTypeName());
                    nodeVo.setValue(appTypeVo.getAppTypeId());
                    nodeVo.setParentId(appTypeVo.getParentId());
                    nodeVo.setNoteType("appType");
                    nodeVo.setMoreInfo(appTypeVo);
                    result.add(nodeVo);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(appList)) {
            //构造应用树节点
            for (App app : appList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(app.getAppId());
                nodeVo.setLabel(app.getAppName());
                nodeVo.setValue(app.getAppId());
                nodeVo.setParentId(app.getAppTypeId());
                nodeVo.setNoteType("app");
                nodeVo.setMoreInfo(app);
                result.add(nodeVo);
            }
        }
        return result;
    }

    /**
     * @Description: 新增应用
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @Override
    public int create(App app) {
        if (StrUtil.isNotBlank(app.getAppId())) {
            throw new BadRequestException("应用ID已存在，不能做新增操作");
        }
        //应用名称不能为空
        if (StrUtil.isBlank(app.getAppName())) {
            throw new BadRequestException("应用名称不能为空");
        }
        //应用入口不能为空
        if (StrUtil.isBlank(app.getAppUrl())) {
            throw new BadRequestException("应用入口不能为空");
        }
        if (null == app.getAppUrlType()) {
            throw new BadRequestException("应用入口类型不能为空");
        }
        //应用分类ID不能为空
        if (StrUtil.isBlank(app.getAppTypeId())) {
            throw new BadRequestException("应用分类ID不能为空");
        }
        //判断应用分类ID是否存在
        //查找应用分类ID
        AppType appType = appTypeDao.selectById(app.getAppTypeId());
        if (appType == null) {
            throw new BadRequestException("应用分类ID不存在");
        }
        //datastatusid默认为1
        if (app.getDatastatusid() == null) {
            app.setDatastatusid(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        app.setCreateBy(currentUser);
        app.setCreateTime(currentDateTime);
        app.setUpdateBy(currentUser);
        app.setUpdateTime(currentDateTime);
        int result = appDao.insert(app);
        return result;
    }

    @Override
    public int update(App app) {
        if (StrUtil.isBlank(app.getAppId())) {
            throw new BadRequestException("应用ID不能为空");
        }else {
            //应用ID不存在
            if (appDao.selectById(app.getAppId()) == null) {
                throw new BadRequestException("应用ID不存在");
            }
        }
        //应用名称不能为空
        if ("".equals(app.getAppName())) {
            throw new BadRequestException("应用名称不能为空");
        }
        //应用入口不能为空
        if ("".equals(app.getAppUrl())) {
            throw new BadRequestException("应用入口不能为空");
        }
        if (null == app.getAppUrlType()) {
            throw new BadRequestException("应用入口类型不能为空");
        }
        //应用分类ID不能为空
        if ("".equals(app.getAppTypeId())) {
            throw new BadRequestException("应用分类ID不能为空");
        }
        //判断应用分类ID是否存在
        //查找应用分类ID
        if (StrUtil.isNotBlank(app.getAppTypeId())) {
            AppType appType = appTypeDao.selectById(app.getAppTypeId());
            if (appType == null) {
                throw new BadRequestException("应用分类ID不存在");
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        app.setUpdateBy(currentUser);
        app.setUpdateTime(currentDateTime);
        int result = appDao.updateById(app);
        return result;
    }

    @Override
    public Object list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<App> query = new Query<App>(queryVO);
            Page<App> page = query.getPage();
            QueryWrapper<App> queryWrapper = query.getQueryWrapper();
            Map<String,Object> paramMap = queryVO.getParamsMap();
            if (MapUtil.isNotEmpty(paramMap) && StrUtil.isNotBlank(MapUtil.getStr(paramMap, "appTypeId"))){
                String appTypeId =  MapUtil.getStr(paramMap, "appTypeId");
                Set<String> appTypeIds = appTypeService.getAppTypeIdsByDown(appTypeId, true);
                queryWrapper.lambda().in(App::getAppTypeId, appTypeIds);
            }
            queryWrapper.orderByAsc("sort").orderByDesc("create_time");
            if (null != page) {
                page = appDao.selectPage(page, queryWrapper);
                //获取App
                List<App> appList = page.getRecords();
                //User转换成UserVo
                List<AppVo> appVoList = convertVo(appList);
                Page<AppVo> appVoPage = new Page<>();
                //User的分页信息复制到UserVo的分页信息
                BeanUtils.copyProperties(page, appVoPage);
                appVoPage.setRecords(appVoList);
                return appVoPage;
            }
            List<App> entityList = appDao.selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(appDao.selectList(new QueryWrapper<App>().orderByAsc("sort").orderByDesc("create_time")));
    }

    /**
     * 查看应用信息(根据ID)
     * @param appId
     * @return
     */
    @Override
    public AppVo detail(String appId) {
        if (!StrUtil.isNotBlank(appId)) {
            throw new BadRequestException("应用ID不能为空");
        }
        App app = appDao.selectById(appId);
        if (app != null) {
            List<App> apps = new ArrayList<>();
            apps.add(app);
            return convertVo(apps).get(0);
        }
        return null;
    }

    /**
     * App转换成AppVo
     * @param appList
     * @return
     */
    public List<AppVo> convertVo(List<App> appList) {
        //应用类别列表
        List<AppType> appTypes = appTypeDao.selectList(new QueryWrapper<AppType>());
        //应用Vo列表
        List<AppVo> appVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(appList)) {
            for (App app : appList) {
                AppVo appVo = new AppVo();
                BeanUtils.copyProperties(app, appVo);
                if (CollectionUtils.isNotEmpty(appTypes)) {
                    //上级组织名称
                    appTypes.forEach(o -> {
                        if (o.getAppTypeId().equals(app.getAppTypeId())) {
                            appVo.setAppTypeName(o.getAppTypeName());
                        }
                    });
                }
                appVoList.add(appVo);
            }
        }
        return appVoList;
    }

}
