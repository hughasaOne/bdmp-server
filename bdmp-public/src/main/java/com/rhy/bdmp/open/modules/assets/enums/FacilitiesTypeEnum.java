package com.rhy.bdmp.open.modules.assets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: yanggj
 * @Description: 设施类型
 * @Date: 2021/9/30 15:57
 * @Version: 1.0.0
 */
@Getter
@AllArgsConstructor
public enum FacilitiesTypeEnum {

    // 监控中心
//    MONITOR_CENTER("monitorCenter", "32330100", "监控中心"),
    // 收费站
    TOLL_STATION("tollStation", "32330200", "收费站", 1),
    // 隧道
    TUNNEL("tunnel", "32330400", "隧道", 3),
    // 外场
//    OUT_FIELD("outField", "32330500", "外场"),
    // 桥梁
    BRIDGE("bridge", "32330600", "桥梁", 4),
    // 门架
    DOOR_FRAME("doorFrame", "32330700", "门架", 2),
    DOOR_FRAME_CHILD1("doorFrame", "32330711", "门架", 2),
    DOOR_FRAME_CHILD2("doorFrame", "32330712", "门架", 2),
    // 服务区
    SERVICE_AREA("serviceArea", "32330800", "服务区", 5);

    private final String name;
    private final String code;
    private final String desc;
    private final Integer order;

    public static List<String> codeList() {
        return Arrays.stream(FacilitiesTypeEnum.values()).map(FacilitiesTypeEnum::getCode).collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }

    public static String getFacTypeName(String code){
        for(FacilitiesTypeEnum typeEnum: values()){
            if(typeEnum.code.equals(code)){
                return typeEnum.desc;
            }
        }
        return null;
    }

    public static FacilitiesTypeEnum getInstance(String code){
        for (FacilitiesTypeEnum facilitiesTypeEnum : FacilitiesTypeEnum.values()) {
            if (facilitiesTypeEnum.getCode().equals(code)){
                return facilitiesTypeEnum;
            }
        }
        return null;
    }

}
