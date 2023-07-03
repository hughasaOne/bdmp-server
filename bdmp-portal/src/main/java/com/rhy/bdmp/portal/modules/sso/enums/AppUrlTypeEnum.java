package com.rhy.bdmp.portal.modules.sso.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
* @description
* @author jiangzhimin
* @date 2021-11-25 11:52
* @version V1.0
**/
@Getter
@AllArgsConstructor
public enum AppUrlTypeEnum {

    FRONT_WEB(1, "前端"),
    BACK_STAGE(2, "后台");

    private final Integer code;
    private final String desc;

    public static boolean includeCode(String code) {
        return Arrays.stream(AppUrlTypeEnum.values()).anyMatch(e -> e.getCode().equals(code));
    }

}