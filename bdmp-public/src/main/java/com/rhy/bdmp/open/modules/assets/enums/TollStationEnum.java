package com.rhy.bdmp.open.modules.assets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: yanggj
 * @Description: TODO
 * @Date: 2021/11/14 10:07
 * @Version: 1.0.0
 */
@Getter
public enum TollStationEnum {
    // 收费站
    NORMAL_TRAFFIC(1, "正常通行收费站", true),
    TEMP_CLOSED(2, "临时关闭收费站", true),
    CONGESTION(3, "拥堵收费站", true),
    NONACTIVATED(4, "未开通收费站", true);

    private final Integer dictKey;
    private final String dictName;
    private final Boolean check;

    TollStationEnum(Integer dictKey, String dictName, Boolean check) {
        this.dictKey = dictKey;
        this.dictName = dictName;
        this.check = check;
    }

    public static String convertDictKeyToName(Integer dictKey) {
        Optional<TollStationEnum> first = Arrays.stream(TollStationEnum.values())
                .filter(tollStationEnum -> tollStationEnum.getDictKey().equals(dictKey))
                .findFirst();
        return first.isPresent() ? first.get().getDictName() : "unknown";
    }

    public static boolean includeDictKey(String dictKey) {
        return Arrays.stream(TollStationEnum.values()).anyMatch(e -> e.getDictKey().equals(dictKey));
    }
}
