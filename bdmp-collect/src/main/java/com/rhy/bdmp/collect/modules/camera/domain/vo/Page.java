package com.rhy.bdmp.collect.modules.camera.domain.vo;

import lombok.Data;

@Data
public class Page {
    private int currentPage;
    private int listRows;
    private int totalRows;
}