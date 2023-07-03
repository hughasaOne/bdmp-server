package com.rhy.bdmp.gateway.modules.domain.to;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class PredicatesTO {
    String name;

    Map<String,String> args = new LinkedHashMap<>();
}
