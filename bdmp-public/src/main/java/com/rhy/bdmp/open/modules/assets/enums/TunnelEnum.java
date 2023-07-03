package com.rhy.bdmp.open.modules.assets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: yanggj
 * @Description: 隧道
 * @Date: 2021/11/14 10:07
 * @Version: 1.0.0
 */
@Getter
public enum TunnelEnum {
    // 隧道
    TUNNEL_TYPE_EXTRA_LONG(1, "特长隧道", true),
    TUNNEL_TYPE_LONG(2, "长隧道", true),
    TUNNEL_TYPE_MIDDLE(3, "中隧道", true),
    TUNNEL_TYPE_SHORT(4, "短隧道", true);

    private final Integer dictKey;
    private final String dictName;
    private final Boolean check;

    TunnelEnum(Integer dictKey, String dictName, Boolean check) {
        this.dictKey = dictKey;
        this.dictName = dictName;
        this.check = check;
    }

    public static String convertDictKeyToName(Integer dictKey) {
        Optional<TunnelEnum> first = Arrays.stream(TunnelEnum.values())
                .filter(tunnelEnum -> tunnelEnum.getDictKey().equals(dictKey))
                .findFirst();
        return first.isPresent() ? first.get().getDictName() : "unknown";
    }

    public static boolean includeDictKey(String dictKey) {
        return Arrays.stream(TunnelEnum.values()).anyMatch(e -> e.getDictKey().equals(dictKey));
    }
}
