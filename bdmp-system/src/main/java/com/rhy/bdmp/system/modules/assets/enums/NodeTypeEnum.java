package com.rhy.bdmp.system.modules.assets.enums;

import java.util.Arrays;

/**
 * @Author: yanggj
 * @Description: 节点类型
 * @Date: 2021/9/28 9:59
 * @Version: 1.0.0
 */
public enum NodeTypeEnum {

    // 节点类型 枚举类
    GROUP("group", "0", "集团"),
    ORG("org", "1", "机构组织(运营公司)"),
    WAY("way", "2", "路段"),
    FAC("fac", "3", "设施(地理位置)");

    private String name;
    private String code;
    private String desc;

    NodeTypeEnum(String name, String code, String desc) {
        this.name = name;
        this.desc = desc;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getName(String code) {
        for (NodeTypeEnum value : NodeTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return "";
    }

    public static boolean include(String code) {
        return Arrays.stream(NodeTypeEnum.values()).anyMatch(e -> e.getCode().equals(code));
    }
    public static boolean excludeByName(String name) {
        return Arrays.stream(NodeTypeEnum.values()).noneMatch(e -> e.getName().equals(name));
    }
}
