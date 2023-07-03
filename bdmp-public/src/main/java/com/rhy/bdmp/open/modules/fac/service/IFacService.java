package com.rhy.bdmp.open.modules.fac.service;

import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.fac.domain.bo.FacDetailBo;

public interface IFacService {
    Object getFacDetail(FacDetailBo facDetailBo);

    Object getFacList(CommonBo commonBo);
}
