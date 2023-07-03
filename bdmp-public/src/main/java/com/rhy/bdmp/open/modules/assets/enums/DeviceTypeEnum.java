package com.rhy.bdmp.open.modules.assets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: jiangzhimin
 * @Description: 设备类型
 * @Date: 2021/11/22 9:59
 * @Version: 1.0.0
 */
@Getter
@AllArgsConstructor
public enum DeviceTypeEnum {

    // 节点类型 枚举类
    SXJ("130100", "摄像机", "SXJ"),
    QBB("130200", "情报板", "QBB"),
    CJQ("130300", "车检器", "CJQ"),
    QXJCZ("131100", "气象监测站", "QXJCZ");

    private String code;
    private String name;
    private String keyword;

    public static String getName(String code) {
        for (DeviceTypeEnum value : DeviceTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return "";
    }

    public static boolean include(String code) {
        return Arrays.stream(DeviceTypeEnum.values()).anyMatch(e -> e.getCode().equals(code));
    }
}
