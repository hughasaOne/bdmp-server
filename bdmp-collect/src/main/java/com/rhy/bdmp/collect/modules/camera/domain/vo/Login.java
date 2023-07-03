package com.rhy.bdmp.collect.modules.camera.domain.vo;

import lombok.Data;

@Data
public class Login {
    private String code;
    private String msg;
    private Rows rows;
}