package com.rhy.bdmp.quartz.modules.syslog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.quartz.modules.sysbusiness.dao.UserDao;
import com.rhy.bdmp.quartz.modules.syslog.dao.SysLogDao;
import com.rhy.bdmp.quartz.modules.syslog.domain.bo.SysLogExt;
import com.rhy.bdmp.quartz.modules.syslog.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shuaichao
 * @create 2022-03-15 13:57
 */
@Service
@Slf4j
public class SysLogServiceImpl implements ISysLogService {

    @Resource
    SysLogDao sysLogDao;

    @Resource
    UserDao userDao;

    @Override
    public Page<SysLogExt> page(SysLogExt sysLogExt, Integer currentPage, Integer size) {
        if (null == currentPage || 0 >= currentPage) {
            currentPage = 1;
        }
        if (null == size || 0 >= size) {
            size = 20;
        }
        String userId = WebUtils.getUserId();
        User user = userDao.selectById(userId);
        boolean isAdmin = user.getIsAdmin() != null && user.getIsAdmin().intValue() == 1;
        Page<SysLogExt> page = new Page<>(currentPage, size);
        return sysLogDao.selectPage(page, sysLogExt, isAdmin, userId);
    }
}
