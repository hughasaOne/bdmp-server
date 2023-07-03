package com.rhy.bdmp.system.modules.sys.domain.bo;

import lombok.Data;

@Data
public class LogBo {
    private Integer currentPage;
    private Integer pageSize;
    private String nickName;
    private String orgName;
    private String desc;
}
