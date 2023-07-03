package com.rhy.bdmp.open.modules.org.service;

import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgDetailBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgListBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgTreeBo;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface IOrgService {
    Object getOrgWayFacTree(CommonBo commonBo);

    Object getOrgTree(OrgTreeBo orgTreeBo);

    Object getOrgList(OrgListBo orgListBo);

    Object detail(OrgDetailBo detailBo);

    Object ipTelStat();

    SXSSFWorkbook exportIpTelStat();
}
