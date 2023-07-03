package com.rhy.bdmp.auth.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.rhy.bdmp.auth.domain.vo.UserVo;
import com.rhy.bcp.common.constant.AuthConstants;
import com.rhy.bcp.common.domain.vo.LoginUserVo;
import com.rhy.bcp.common.resutl.ResultCode;
import com.rhy.bdmp.auth.service.IAuthClientDetailsService;
import com.rhy.bdmp.auth.service.UserService;
import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description: 自定义用户认证
 * @version: 1.0.0
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private HttpServletRequest request;

    @Resource
    private UserService userService;

    @Resource
    private IAuthClientDetailsService authClientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter(AuthConstants.JWT_CLIENT_ID_KEY);
        LoginUserVo userRoleVo = null;
        UserVo userVo = null;
        User user = userService.getUserByUsername(username);
        if (ObjectUtil.isNotNull(user)) {
            List<String> roleIds = userService.findRoleIdsByUserId(user.getUserId());
            userRoleVo = new LoginUserVo();
            BeanUtil.copyProperties(user, userRoleVo);
            userRoleVo.setRoles(roleIds);
        }
        if (ObjectUtil.isNull(userRoleVo)) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        userRoleVo.setClientId(clientId);
        userVo = new UserVo(userRoleVo);
        userVo.setNickName(user.getNickName());
        //查询appId;
        AuthClientDetails authClientDetails = authClientDetailsService.selectByClientId(clientId);
        if (ObjectUtil.isNotNull(authClientDetails)) {
            user.setAppId(authClientDetails.getAppId());
        }
        if (!userVo.isEnabled()) {
            throw new DisabledException("该账户已被禁用，请联系管理员!");
        } else if (!userVo.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定，请联系管理员!");
        } else if (!userVo.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期，请联系管理员!");
        } else if (!userVo.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
        }
        userService.setUserInfo(userVo,userVo.getOrgId());
        return userVo;
    }

}
