package com.rhy.bdmp.collect.modules.camera.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ApiCamerResponse {
    private Page pagination;
    private List<Camera> data;
}
