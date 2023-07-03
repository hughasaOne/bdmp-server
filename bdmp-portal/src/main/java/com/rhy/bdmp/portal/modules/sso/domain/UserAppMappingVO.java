package com.rhy.bdmp.portal.modules.sso.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author: lipeng
 * @Date: 2021/5/11
 * @description: 用户映射VO
 * @version: 1.0
 */

@Data
@NoArgsConstructor
public class UserAppMappingVO {

    private String userId;
    private String userName;
    private Set<AppUserVO> userMappings;
}
