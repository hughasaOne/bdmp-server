package com.rhy.bdmp.quartz.modules.sysbusiness.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.bo.SysBusinessExt;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.vo.SysBusinessExtVo;

import java.util.List;

/**
 * @author shuaichao
 * @create 2022-03-15 14:35
 */
public interface ISysBusinessService {


    List<SysBusinessExt> list(SysBusiness sysBusiness);

    int create(SysBusiness sysBusiness);

    int update(SysBusiness sysBusiness);

    int delete(List<String> sysBusinessIds);

    List<Tree<String>> treeList();

    User getUserById();
}
