package com.rhy.bdmp.system.modules.sys.service;

import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.system.modules.sys.domain.bo.LogBo;
import com.rhy.bdmp.system.modules.sys.domain.vo.LogVo;

public interface ILogService {
    PageUtil<LogVo> page(LogBo logBo);
}
