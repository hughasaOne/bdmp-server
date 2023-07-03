package com.rhy.bdmp.system.modules.gateway.domain.vo;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @author PSQ
 */
@Data
public class JsonVO {

    private String name;

    private LinkedHashMap<String, String> args;
}
