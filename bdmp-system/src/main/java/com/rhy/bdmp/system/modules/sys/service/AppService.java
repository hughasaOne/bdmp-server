package com.rhy.bdmp.system.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppVo;

import java.util.List;
import java.util.Set;

public interface AppService {

    /**
    * @Description: 删除
    * @Author: dongyu
    * @Date: 2021/4/14
    */
    void delete(Set<String> appIds);

    /**
    * @Description: 查询应用类型与应用关系
    * @Author: dongyu
    * @Date: 2021/4/15
     * @param isUseUserPermissions
    */
    List<NodeVo> findAppTypeAndAppTree(Boolean isUseUserPermissions);

    /**
    * @Description: 新增应用
    * @Author: dongyu
    * @Date: 2021/4/20
     * @param app
    */
    int create(App app);

    /**
    * @Description: 修改应用
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int update(App app);

    /**
     * 应用信息列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);

    /**
     * 查看应用信息(根据ID)
     * @param appId
     * @return
     */
    AppVo detail(String appId);

}
