package com.rhy.bdmp.portal.modules.sso.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lipeng
 * @Date: 2021/5/11
 * @description: 应用用户VO
 * @version: 1.0
 */

@Data
@NoArgsConstructor
public class AppUserVO {

    private String appId;
    private String appUserId;
    private String appUserName;
    private String nikeNmae;
    private String phone;
    private String email;

}
