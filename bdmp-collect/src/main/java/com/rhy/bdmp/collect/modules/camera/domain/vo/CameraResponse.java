package com.rhy.bdmp.collect.modules.camera.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class CameraResponse {
    private int currentPage;
    private int listRows;
    private int totalRows;
    private List<Camera> data;
}
