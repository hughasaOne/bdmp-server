package com.rhy.bdmp.collect.modules.camera.domain.vo;

import java.util.List;

@lombok.Data
public class TrreRows {
    private int first;
    private long limit;
    private int offset;
    private int pageNumber;
    private int pageSize;
    private List<Data> rows;
    private int total;
    private int totalPages;
}
