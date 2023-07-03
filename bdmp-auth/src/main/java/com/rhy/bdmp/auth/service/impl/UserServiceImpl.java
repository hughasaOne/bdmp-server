package com.rhy.bdmp.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.auth.dao.UserDao;
import com.rhy.bdmp.auth.domain.vo.UserVo;
import com.rhy.bdmp.auth.service.UserService;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictService;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User对象对应服务层
 *
 * @author PSQ
 */
@Service(value = "myUserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private IBaseDictService dictService;

    @Resource
    private IBaseOrgService orgService;

    /**
     * 查看用户(根据username)
     *
     * @param username 用户名
     * @return user对象
     */
    @Override
    public User getUserByUsername(String username) {
        if (!StrUtil.isNotBlank(username)) {
            throw new BadRequestException("账号不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("app_id", "1");
        return userDao.selectOne(queryWrapper);
    }

    /**
     * 查询用户角色权限（根据用户ID,返回角色ID集合）
     *
     * @param userId 用户id
     * @return 权限集合
     */
    @Override
    public List<String> findRoleIdsByUserId(String userId) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)) {
            result = userDao.findRoleIdsByUserId(userId);
        }
        return result;
    }

    @Override
    public void setUserInfo(UserVo userVo,String orgId) {
        Org org = orgService.getById(orgId);
        if (null == org){
            return;
        }
        String orgType = org.getOrgType();
        if ("000300".equals(orgType)){
            userVo.setOperationGroupId(org.getParentId());
            userVo.setOperationCompanyId(org.getOrgId());
        }
        else if("000200".equals(orgType)){
            userVo.setOperationGroupId(org.getOrgId());
            userVo.setOperationCompanyId(null);
        }
        else if(StrUtil.isBlank(org.getParentId()) || "000900".equals(orgType)){
            userVo.setOperationCompanyId(null);
            userVo.setOperationGroupId(null);
        }
        else {
            this.setUserInfo(userVo,org.getParentId());
        }
    }
}
