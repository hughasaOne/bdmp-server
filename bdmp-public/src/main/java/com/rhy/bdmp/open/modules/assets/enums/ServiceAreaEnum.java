package com.rhy.bdmp.open.modules.assets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: yanggj
 * @Description: 服务区
 * @Date: 2021/11/14 10:07
 * @Version: 1.0.0
 */
@Getter
public enum ServiceAreaEnum {
    // 服务区
    ONE_PLUS(1, "1+类", true),
    ONE(2, "1类", true),
    TWO(3, "2类", true),
    THREE_FOUR(4, "3、4类", true);

    private final Integer dictKey;
    private final String dictName;
    private final Boolean check;

    ServiceAreaEnum(Integer dictKey, String dictName, Boolean check) {
        this.dictKey = dictKey;
        this.dictName = dictName;
        this.check = check;
    }

    public static String convertDictKeyToName(Integer dictKey) {
        Optional<ServiceAreaEnum> first = Arrays.stream(ServiceAreaEnum.values())
                .filter(serviceAreaEnum -> serviceAreaEnum.getDictKey().equals(dictKey))
                .findFirst();
        return first.isPresent() ? first.get().getDictName() : "unknown";
    }

    public static boolean includeDictKey(String dictKey) {
        return Arrays.stream(ServiceAreaEnum.values()).anyMatch(e -> e.getDictKey().equals(dictKey));
    }
}
