package com.rhy.bdmp.open.modules.assets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: yanggj
 * @Description: 门架
 * @Date: 2021/11/14 10:07
 * @Version: 1.0.0
 */
@Getter
public enum DoorFrameEnum {

    // 门架
    PROVINCE(1, "省界门架", true),
    NORMAL(2, "普通门架", true);


    private final Integer dictKey;
    private final String dictName;
    private final Boolean check;

    DoorFrameEnum(Integer dictKey, String dictName, Boolean check) {
        this.dictKey = dictKey;
        this.dictName = dictName;
        this.check = check;
    }

    public static String convertDictKeyToName(Integer dictKey) {
        Optional<DoorFrameEnum> first = Arrays.stream(DoorFrameEnum.values())
                .filter(doorFrameEnum -> doorFrameEnum.getDictKey().equals(dictKey))
                .findFirst();
        return first.isPresent() ? first.get().getDictName() : "unknown";
    }

    public static boolean includeDictKey(String dictKey) {
        return Arrays.stream(DoorFrameEnum.values()).anyMatch(e -> e.getDictKey().equals(dictKey));
    }
}
