package com.rhy.bdmp.system.modules.assets.domain.bo;

import lombok.Data;

@Data
public class IPTelListBo {
    private String dirId;
    private String name;
    private Integer datastatusid;
    private Integer status;
    private Integer pageSize;
    private Integer currentPage;
}
