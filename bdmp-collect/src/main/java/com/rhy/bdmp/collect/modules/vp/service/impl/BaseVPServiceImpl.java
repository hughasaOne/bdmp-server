package com.rhy.bdmp.collect.modules.vp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bdmp.collect.modules.vp.dao.BaseVPDao;
import com.rhy.bdmp.collect.modules.vp.domain.po.Resource;
import com.rhy.bdmp.collect.modules.vp.service.IBaseVPService;
import org.springframework.stereotype.Service;

@Service
public class BaseVPServiceImpl extends ServiceImpl<BaseVPDao, Resource> implements IBaseVPService {
}
