package com.rhy.bdmp.collect.modules.camera.domain.vo;

import lombok.Data;

@Data
public class Token {
    private String  accessToken;
    private String  expiresIn;
    private String  tokenType;
}