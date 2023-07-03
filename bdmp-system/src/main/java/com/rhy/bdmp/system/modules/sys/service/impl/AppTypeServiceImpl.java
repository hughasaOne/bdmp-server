package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.rhy.bdmp.system.modules.sys.dao.AppDao;
import com.rhy.bdmp.system.modules.sys.dao.AppTypeDao;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppTypeVo;
import com.rhy.bdmp.system.modules.sys.service.AppTypeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AppTypeServiceImpl implements AppTypeService {

    @Resource
    private AppTypeDao appTypeDao;

    @Resource
    private AppDao appDao;

    /**
     * @Description: 删除应用类别
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Transactional
    @Override
    public void delete(Set<String> appTypeIds) {
        if (CollectionUtils.isNotEmpty(appTypeIds)) {
            //如果只传一个id，判断id是否存在
            if (appTypeIds.size() == 1) {
                if (appTypeDao.selectById(String.valueOf(appTypeIds.toArray()[0])) == null) {
                    throw new BadRequestException("应用类别ID不存在");
                }
            }
            for (String appTypeId : appTypeIds) {
                //判断应用类别是否存在下级
                //查找应用类别的子类别（不包含当前节点）
                List<String> appTypeChildrenIds = appTypeDao.findAppTypeChildrenIds(appTypeId);
                if (CollectionUtils.isNotEmpty(appTypeChildrenIds)) {
                    throw new BadRequestException("应用类别存在子应用类别");
                }
            }
            //判断是否有关联应用
            List<App> apps = appDao.selectList(new QueryWrapper<App>().in("app_type_id",appTypeIds));
            if (CollectionUtils.isNotEmpty(apps)) {
                throw new BadRequestException("应用类别存在关联应用");
            }
            //删除应用类别
            appTypeDao.deleteBatchIds(appTypeIds);
        }
    }

    /**
    * @Description: 新增应用类别
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    @Override
    public int create(AppType appType) {
        if (StrUtil.isNotBlank(appType.getAppTypeId())) {
            throw new BadRequestException("应用类别ID已存在，不能做新增操作");
        }
        //应用类别名称不能为空
        if (StrUtil.isBlank(appType.getAppTypeName())) {
            throw new BadRequestException("应用类别名称不能为空");
        }
        //应用类别parentId是否存在
        if (StrUtil.isNotBlank(appType.getParentId())) {
            AppType parent = appTypeDao.selectById(appType.getParentId());
            if (parent == null) {
                throw new BadRequestException("应用类别的parentId不存在");
            }
        }
        //datastatusid默认为1
        if (appType.getDatastatusid() == null) {
            appType.setDatastatusid(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        appType.setCreateBy(currentUser);
        appType.setCreateTime(currentDateTime);
        appType.setUpdateBy(currentUser);
        appType.setUpdateTime(currentDateTime);
        int result = appTypeDao.insert(appType);
        return result;
    }

    /**
    * @Description: 修改应用类别
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    @Override
    public int update(AppType appType) {
        if (StrUtil.isBlank(appType.getAppTypeId())) {
            throw new BadRequestException("应用类别ID不能为空");
        }else{
            //应用分类ID不存在
            if (appTypeDao.selectById(appType.getAppTypeId()) == null) {
                throw new BadRequestException("应用分类ID不存在");
            }
        }
        //应用类别名称不能为空
        if ("".equals(appType.getAppTypeName())) {
            throw new BadRequestException("应用类别名称不能为空");
        }
        //应用类别parentId是否存在
        if (StrUtil.isNotBlank(appType.getParentId())) {
            AppType parent = appTypeDao.selectById(appType.getParentId());
            if (parent == null) {
                throw new BadRequestException("应用类别的parentId不存在");
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        appType.setUpdateBy(currentUser);
        appType.setUpdateTime(currentDateTime);
        int result = appTypeDao.updateById(appType);
        return result;
    }

    /**
     * 应用类别列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public Object list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<AppType> query = new Query<AppType>(queryVO);
            Page<AppType> page = query.getPage();
            QueryWrapper<AppType> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = appTypeDao.selectPage(page, queryWrapper);
                //获取AppType
                List<AppType> appTypeList = page.getRecords();
                //AppType转换成AppTypeVo
                List<AppTypeVo> appTypeVoList = convertVo(appTypeList);
                Page<AppTypeVo> appTypeVoPage = new Page<>();
                //AppType的分页信息复制到AppTypeVo的分页信息
                BeanUtils.copyProperties(page, appTypeVoPage);
                appTypeVoPage.setRecords(appTypeVoList);
                return appTypeVoPage;
            }
            List<AppType> entityList = appTypeDao.selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(appTypeDao.selectList(new QueryWrapper<AppType>().orderByAsc("sort").orderByDesc("create_time")));
    }

    /**
     * AppType转换成AppTypeVo
     * @param appTypeList
     * @return
     */
    public List<AppTypeVo> convertVo(List<AppType> appTypeList) {
        //应用类别列表
        List<AppType> appTypes = appTypeDao.selectList(new QueryWrapper<AppType>());
        //应用类别Vo列表
        List<AppTypeVo> appTypeVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(appTypeList)) {
            for (AppType appType : appTypeList) {
                AppTypeVo appTypeVo = new AppTypeVo();
                BeanUtils.copyProperties(appType, appTypeVo);
                if (CollectionUtils.isNotEmpty(appTypes)) {
                    //上级组织名称
                    appTypes.forEach(o -> {
                        if (o.getAppTypeId().equals(appType.getParentId())) {
                            appTypeVo.setParentName(o.getAppTypeName());
                        }
                    });
                }
                appTypeVoList.add(appTypeVo);
            }
        }
        return appTypeVoList;
    }

    /**
     * @Description: 查询应用类型子节点（根据应用类型ID）
     * @Author: dongyu
     * @Date: 2021/5/20
     */
    @Override
    public List<AppTypeVo> findAppTypeChildren(String parentId, String includeId, QueryVO queryVO) {
        List<AppTypeVo> appTypeList = new ArrayList<>();
        Query<AppTypeVo> query = new Query<>(queryVO);
        QueryWrapper<AppTypeVo> queryWrapper = query.getQueryWrapper();
        appTypeList = appTypeDao.findAppTypeChildren(parentId, includeId, queryWrapper);
        return appTypeList;
    }

    /**
     * 通过appTypeId向下查找包含的所有appTypeId及自身
     * @param appTypeId  应用类型ID
     * @param isSelf 是否包含自身
     * @return
     */
    @Override
    public Set<String> getAppTypeIdsByDown(String appTypeId, Boolean isSelf){
        Set<String> result = new HashSet<>();
        if (isSelf){
            result.add(appTypeId);
        }
        getAppTypeIdsByDown(appTypeId, result);
        return result;
    }

    private void getAppTypeIdsByDown(String appTypeId, Set<String> result){
        List<AppType> appTypeList = appTypeDao.selectList(new QueryWrapper<AppType>().lambda().eq(AppType::getParentId, appTypeId));
        for (AppType appType : appTypeList){
            result.add(appType.getAppTypeId());
            getAppTypeIdsByDown(appType.getAppTypeId(), result);
        }
    }
}
