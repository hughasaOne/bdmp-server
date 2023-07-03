package com.rhy.bdmp.system.modules.sys.domain.vo;

import com.rhy.bdmp.base.modules.sys.domain.po.Log;
import lombok.Data;

@Data
public class LogVo extends Log {
    private String nickName;
    private String orgName;
    private String orgId;
}
