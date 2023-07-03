package com.rhy.bdmp.system.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.system.modules.sys.dao.LogDao;
import com.rhy.bdmp.system.modules.sys.domain.bo.LogBo;
import com.rhy.bdmp.system.modules.sys.domain.vo.LogVo;
import com.rhy.bdmp.system.modules.sys.service.ILogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("logService1")
public class LogServiceImpl implements ILogService {

    @Resource(name = "logDao1")
    private LogDao logDao;

    /**
     * 日志列表查询(分页)
     */
    @Override
    public PageUtil<LogVo> page(LogBo logBo) {
        Page<LogVo> page = new Page<>();
        page.setSize(logBo.getPageSize());
        page.setCurrent(logBo.getCurrentPage());

        return new PageUtil<LogVo>(logDao.page(page,logBo));
    }
}
