package com.rhy.bdmp.open.modules.org.domain.vo;

import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import lombok.Data;

@Data
public class BaseOrgVo extends Org {
    private String orgTypeName;
}
