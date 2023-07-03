package com.rhy.bdmp.open.modules.assets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: yanggj
 * @Description: 路产概况字典类型
 * @Date: 2021/11/10 10:03
 * @Version: 1.0.0
 */
@Getter
public enum ExpresswayAssetsEnum {

    // 收费站
    CHARGE_STATION_STATUS_NORMAL_TRAFFIC(FacilitiesTypeEnum.TOLL_STATION.getCode(), "chargeStationStatus", 1, "正常通行收费站"),
    CHARGE_STATION_STATUS_TEMP_CLOSED(FacilitiesTypeEnum.TOLL_STATION.getCode(), "chargeStationStatus", 2, "临时关闭收费站"),
    CHARGE_STATION_STATUS_CONGESTION(FacilitiesTypeEnum.TOLL_STATION.getCode(), "chargeStationStatus", 3, "拥堵收费站"),
    // 门架
    GANTRY_TYPE_PROVINCE(FacilitiesTypeEnum.DOOR_FRAME.getCode(), "gantryType", 1, "省界门架"),
    GANTRY_TYPE_NORMAL(FacilitiesTypeEnum.DOOR_FRAME.getCode(), "gantryType", 2, "普通门架"),
    // 桥梁
    BRIDGE_TYPE_EXTRA_LARGE(FacilitiesTypeEnum.BRIDGE.getCode(), "bridgeType", 1, "特大桥"),
    BRIDGE_TYPE_LARGE(FacilitiesTypeEnum.BRIDGE.getCode(), "bridgeType", 2, "大桥"),
    BRIDGE_TYPE_MIDDLE(FacilitiesTypeEnum.BRIDGE.getCode(), "bridgeType", 3, "中桥"),
    BRIDGE_TYPE_SMALL(FacilitiesTypeEnum.BRIDGE.getCode(), "bridgeType", 4, "小桥"),
    // 隧道
    TUNNEL_TYPE_EXTRA_LONG(FacilitiesTypeEnum.TUNNEL.getCode(), "tunnelType", 1, "特长隧道"),
    TUNNEL_TYPE_LONG(FacilitiesTypeEnum.TUNNEL.getCode(), "tunnelType", 2, "长隧道"),
    TUNNEL_TYPE_MIDDLE(FacilitiesTypeEnum.TUNNEL.getCode(), "tunnelType", 3, "中隧道"),
    TUNNEL_TYPE_SHORT(FacilitiesTypeEnum.TUNNEL.getCode(), "tunnelType", 4, "短隧道"),
    // 服务区
    SERVICE_AREA_TYPE_ONE_PLUS(FacilitiesTypeEnum.SERVICE_AREA.getCode(), "serviceAreaType", 1, "1+类"),
    SERVICE_AREA_TYPE_ONE(FacilitiesTypeEnum.SERVICE_AREA.getCode(), "serviceAreaType", 2, "1类"),
    SERVICE_AREA_TYPE_TWO(FacilitiesTypeEnum.SERVICE_AREA.getCode(), "serviceAreaType", 3, "2类"),
    SERVICE_AREA_TYPE_THREE_FOUR(FacilitiesTypeEnum.SERVICE_AREA.getCode(), "serviceAreaType", 4, "3、4类"),
    ;


    private final String code;
    private final String dictType;
    private final Integer dictKey;
    private final String dictName;

    ExpresswayAssetsEnum(String code, String dictType, Integer dictKey, String dictName) {
        this.code = code;
        this.dictType = dictType;
        this.dictKey = dictKey;
        this.dictName = dictName;
    }

    public static boolean include(String dictType, Integer dictKey) {
        return Arrays.stream(ExpresswayAssetsEnum.values()).anyMatch(e -> e.getDictType().equals(dictType) && e.getDictKey().equals(dictKey));
    }

    public static String convertDictKeyToName(String code, Integer dictKey) {
        Optional<ExpresswayAssetsEnum> first = Arrays.stream(ExpresswayAssetsEnum.values())
                .filter(expresswayAssetsEnum -> expresswayAssetsEnum.getCode().equals(code) && expresswayAssetsEnum.getDictKey().equals(dictKey))
                .findFirst();
        if (first.isPresent()) {
            return first.get().getDictName();
        } else {
            return "unknown";
        }
    }

}
