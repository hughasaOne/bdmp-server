package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.sys.dao.AppDao;
import com.rhy.bdmp.system.modules.sys.dao.UserDao;
import com.rhy.bdmp.system.modules.sys.dao.UserMappingDao;
import com.rhy.bdmp.system.modules.sys.domain.vo.UserMappingVo;
import com.rhy.bdmp.system.modules.sys.service.UserMappingService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserMappingServiceImpl implements UserMappingService {

    @Resource
    private UserMappingDao userMappingDao;

    @Resource
    private UserDao userDao;

    @Resource
    private AppDao appDao;

    /**
     * 新增用户映射
     * @param userMapping
     * @return
     */
    @Override
    public int create(UserMapping userMapping) {
        if (StrUtil.isNotBlank(userMapping.getUserMappingId())) {
            throw new BadRequestException("用户映射ID已存在，不能做新增操作");
        }
        //用户id不能为空
        if (StrUtil.isBlank(userMapping.getUserId())) {
            throw new BadRequestException("用户ID不能为空");
        }
        //应用用户ID不能为空
        if (StrUtil.isBlank(userMapping.getAppUserId())) {
            throw new BadRequestException("应用用户ID不能为空");
        }
        //应用id不能为空
        if (StrUtil.isBlank(userMapping.getAppId())) {
            throw new BadRequestException("应用ID不能为空");
        }
        //用户id是否存在
        if (userDao.selectById(userMapping.getUserId()) == null) {
            throw new BadRequestException("用户ID不存在");
        }
        //应用id是否存在
        if (appDao.selectById(userMapping.getAppId()) == null) {
            throw new BadRequestException("应用ID不存在");
        }
        /*//查询用户映射(根据用户ID、应用ID)
        UserMappingVo userMappingVoByUserIdAndAppId = userMappingDao.findUserMappingByUserIdAndAppId(userMapping.getUserId(), userMapping.getAppId());
        //同一个用户不能重复映射同一个应用
        if (userMappingVoByUserIdAndAppId != null) {
            throw new BadRequestException("平台用户："+userMappingVoByUserIdAndAppId.getNickName()+"，已关联应用："+userMappingVoByUserIdAndAppId.getAppName()+"，已关联应用用户：[用户名："+userMappingVoByUserIdAndAppId.getAppUserName()+"，账号："+userMappingVoByUserIdAndAppId.getAppUserAccount()+"]");
        }
        //查询用户映射(根据用户ID、应用下用户ID)
        UserMappingVo userMappingByAppIdAndAppUserId = userMappingDao.findUserMappingByAppIdAndAppUserId(userMapping.getAppId(), userMapping.getAppUserId());
        //同一个应用下应用用户Id不能重复
        if (StrUtil.isNotBlank(userMapping.getAppUserId())) {
            if (userMappingByAppIdAndAppUserId != null) {
                throw new BadRequestException("应用："+userMappingByAppIdAndAppUserId.getAppName()+"，应用用户：[用户名："+userMappingByAppIdAndAppUserId.getAppUserName()+"，账号："+userMappingByAppIdAndAppUserId.getAppUserAccount()+"]，已关联平台用户：[用户名："+userMappingByAppIdAndAppUserId.getNickName()+"，账号："+userMappingByAppIdAndAppUserId.getUserAccount()+"]");
            }
        }*/

        // 基础数据下的用户的映射用户不重复
        boolean isRepeat = userMappingDao.checkAppSubUserRepeat(userMapping);
        if (isRepeat){
            throw new BadRequestException("当前选择的户已关联");
        }

        // 基础数据下的用户映射同一个app下的用户不超过一个
        boolean isMultiple = userMappingDao.checkAppSubuserMultiple(userMapping);
        if (isMultiple){
            throw new BadRequestException("当前选择的app下已有用户关联");
        }


        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        userMapping.setCreateBy(currentUser);
        userMapping.setCreateTime(currentDateTime);
        userMapping.setUpdateBy(currentUser);
        userMapping.setUpdateTime(currentDateTime);
        int result = userMappingDao.insert(userMapping);
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
            throw new BadRequestException("用户映射ID不能为空");
        }else {
            //用户映射ID不存在
            if (userMappingDao.selectById(userMapping.getUserMappingId()) == null) {
                throw new BadRequestException("用户映射ID不存在");
            }
        }
        //用户id不能为空
        if ("".equals(userMapping.getUserId())) {
            throw new BadRequestException("用户ID不能为空");
        }
        //应用用户ID不能为空
        if ("".equals(userMapping.getAppUserId())) {
            throw new BadRequestException("应用用户ID不能为空");
        }
        //应用id不能为空
        if ("".equals(userMapping.getAppId())) {
            throw new BadRequestException("应用ID不能为空");
        }
        //用户id是否存在
        if (StrUtil.isNotBlank(userMapping.getUserId())) {
            if (userDao.selectById(userMapping.getUserId()) == null) {
                throw new BadRequestException("用户ID不存在");
            }
        }
        //应用id是否存在
        if (StrUtil.isNotBlank(userMapping.getAppId())) {
            if (appDao.selectById(userMapping.getAppId()) == null) {
                throw new BadRequestException("应用ID不存在");
            }
        }
        //查询用户映射(根据用户ID、应用ID)
        UserMappingVo userMappingVoByUserIdAndAppId = userMappingDao.findUserMappingByUserIdAndAppId(userMapping.getUserId(), userMapping.getAppId());
        //同一个用户不能重复映射同一个应用
        if (userMappingVoByUserIdAndAppId != null && !userMappingVoByUserIdAndAppId.getUserMappingId().equals(userMapping.getUserMappingId())) {
            throw new BadRequestException("平台用户：" + userMappingVoByUserIdAndAppId.getNickName() + "，已关联应用：" + userMappingVoByUserIdAndAppId.getAppName() + "，已关联应用用户：[用户名：" + userMappingVoByUserIdAndAppId.getAppUserName() + "，账号：" + userMappingVoByUserIdAndAppId.getAppUserAccount() + "]");
        }
        //查询用户映射(根据用户ID、应用下用户ID)
        UserMappingVo userMappingByAppIdAndAppUserId = userMappingDao.findUserMappingByAppIdAndAppUserId(userMapping.getAppId(), userMapping.getAppUserId());
        //同一个应用下应用用户Id不能重复
        if (StrUtil.isNotBlank(userMapping.getAppUserId())) {
            if (userMappingByAppIdAndAppUserId != null && !userMappingByAppIdAndAppUserId.getUserMappingId().equals(userMapping.getUserMappingId())) {
                throw new BadRequestException("应用：" + userMappingByAppIdAndAppUserId.getAppName() + "，应用用户：[用户名：" + userMappingByAppIdAndAppUserId.getAppUserName() + "，账号：" + userMappingByAppIdAndAppUserId.getAppUserAccount() + "]，已关联平台用户：[用户名：" + userMappingByAppIdAndAppUserId.getNickName() + "，账号：" + userMappingByAppIdAndAppUserId.getUserAccount() + "]");
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        userMapping.setUpdateBy(currentUser);
        userMapping.setUpdateTime(currentDateTime);
        int result = userMappingDao.updateById(userMapping);
        return result;
    }

    /**
    * @Description: 删除用户映射
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    @Override
    public void delete(Set<String> userMappingIds) {
        if (CollectionUtils.isNotEmpty(userMappingIds)) {
            //如果只传一个id，判断id是否存在
            if (userMappingIds.size() == 1) {
                if (userMappingDao.selectById(String.valueOf(userMappingIds.toArray()[0])) == null) {
                    throw new BadRequestException("用户映射ID不存在");
                }
            }
            userMappingDao.deleteBatchIds(userMappingIds);
        }
    }

    /**
     * 用户映射列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public Object list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<UserMapping> query = new Query<UserMapping>(queryVO);
            Page<UserMapping> page = query.getPage();
            QueryWrapper<UserMapping> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = userMappingDao .selectPage(page, queryWrapper);
                //获取Role
                List<UserMapping> userMappingList = page.getRecords();
                //Role转换成RoleVo
                List<UserMappingVo> userMappingVoList = convertVo(userMappingList);
                Page<UserMappingVo> userMappingVoPage = new Page<>();
                //Role的分页信息复制到RoleVo的分页信息
                BeanUtils.copyProperties(page, userMappingVoPage);
                userMappingVoPage.setRecords(userMappingVoList);
                return userMappingVoPage;
            }
            List<UserMapping> entityList = userMappingDao .selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(userMappingDao .selectList(Wrappers.emptyWrapper()));
    }

    /**
     * UserMapping转换成UserMappingVo
     * @param userMappingList
     * @return
     */
    public List<UserMappingVo> convertVo(List<UserMapping> userMappingList) {
        //应用列表
        List<App> apps = appDao.selectList(new QueryWrapper<App>());
        //用户列表
        List<User> users = userDao.selectList(new QueryWrapper<>());
        //角色Vo列表
        List<UserMappingVo> userMappingVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userMappingList)) {
            for (UserMapping userMapping : userMappingList) {
                UserMappingVo userMappingVo = new UserMappingVo();
                BeanUtils.copyProperties(userMapping, userMappingVo);
                if (CollectionUtils.isNotEmpty(apps)) {
                    //所属应用名称
                    apps.forEach(o -> {
                        if (o.getAppId().equals(userMapping.getAppId())) {
                            userMappingVo.setAppName(o.getAppName());
                        }
                    });
                }
                if (CollectionUtils.isNotEmpty(users)) {
                    users.forEach(o -> {
                        //用户名
                        if (o.getUserId().equals(userMapping.getUserId())) {
                            userMappingVo.setNickName(o.getNickName());
                        }
                        //应用用户名
                        if (o.getUserId().equals(userMapping.getAppUserId())) {
                            userMappingVo.setAppUserName(o.getNickName());
                        }
                    });
                }
                userMappingVoList.add(userMappingVo);
            }
        }
        return userMappingVoList;
    }
}
