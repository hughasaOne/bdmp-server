package com.rhy.bdmp.open.modules.assets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: yanggj
 * @Description: 桥梁分类
 * @Date: 2021/11/14 10:07
 * @Version: 1.0.0
 */
@Getter
public enum BridgeEnum {
    // 桥梁类型
    EXTRA_LARGE(1, "特大桥", true),
    LARGE(2, "大桥", true),
    MIDDLE(3, "中桥", true),
    SMALL(4, "小桥", true);

    private final Integer dictKey;
    private final String dictName;
    private final Boolean check;

    BridgeEnum(Integer dictKey, String dictName, Boolean check) {
        this.dictKey = dictKey;
        this.dictName = dictName;
        this.check = check;
    }

    public static String convertDictKeyToName(Integer dictKey) {
        Optional<BridgeEnum> first = Arrays.stream(BridgeEnum.values())
                .filter(bridgeEnum -> bridgeEnum.getDictKey().equals(dictKey))
                .findFirst();
        return first.isPresent() ? first.get().getDictName() : "unknown";
    }

    public static boolean includeDictKey(String dictKey) {
        return Arrays.stream(BridgeEnum.values()).anyMatch(e -> e.getDictKey().equals(dictKey));
    }
}
