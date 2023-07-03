package com.rhy.bdmp.system.modules.assets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description 用户数据权限类型
 * @date 2021-10-08 11:10
 **/
@Getter
@AllArgsConstructor
public enum PermissionsEnum {

    ORG("1", "运营公司"),
    WAY("2", "路段"),
    FAC("3", "设施");

    private final String code;
    private final String name;

    public static PermissionsEnum find(String val) {
        for (PermissionsEnum dataScopeEnum : PermissionsEnum.values()) {
            if (val.equals(dataScopeEnum.getCode())) {
                return dataScopeEnum;
            }
        }
        return null;
    }

    public static boolean include(String code) {
        return Arrays.stream(PermissionsEnum.values()).anyMatch(e -> e.getCode().equals(code));
    }

}