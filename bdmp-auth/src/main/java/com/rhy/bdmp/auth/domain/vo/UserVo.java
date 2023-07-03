package com.rhy.bdmp.auth.domain.vo;


import com.rhy.bcp.common.constant.AuthConstants;
import com.rhy.bcp.common.domain.vo.LoginUserVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: lipeng
 * @Date: 2021/1/5
 * @description:  登录用户信息
 * @version: 1.0.0
 */
@Data
@NoArgsConstructor
public class UserVo implements UserDetails {

    private String id;

    private String username;

    private String nickName;

    private String password;

    private String orgId;

    private Boolean enabled;

    private String clientId;

    private String appId;

    private String operationGroupId;

    private String operationCompanyId;

    private Collection<SimpleGrantedAuthority> authorities;

    public UserVo(LoginUserVo user) {
        this.setId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setPassword(AuthConstants.BCRYPT + user.getPassword());
        this.setOrgId(user.getOrgId());
        this.setEnabled(Integer.valueOf(1).equals(user.getDatastatusid()));
            this.setClientId(user.getClientId());
            if (user.getRoles() != null) {
                authorities = new ArrayList<>();
            user.getRoles().forEach(roleId -> authorities.add(new SimpleGrantedAuthority(String.valueOf(roleId))));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
