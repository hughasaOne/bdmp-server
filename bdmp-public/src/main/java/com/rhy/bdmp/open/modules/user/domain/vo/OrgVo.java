package com.rhy.bdmp.open.modules.user.domain.vo;

import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import lombok.Data;

import javax.annotation.Resource;

@Data
public class OrgVo extends Org {
    @Resource
    private String parentName;

    @Resource
    private String parentShortName;
}
