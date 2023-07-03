package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 应用信息 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseAppService extends IService<App> {

    /**
     * 应用信息列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<App> list(QueryVO queryVO);

    /**
     * 应用信息列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<App> page(QueryVO queryVO);

    /**
     * 查看应用信息(根据ID)
     * @param appId
     * @return
     */
    App detail(String appId);

    /**
     * 新增应用信息
     * @param app
     * @return
     */
    int create(App app);

    /**
     * 修改应用信息
     * @param app
     * @return
     */
    int update(App app);

    /**
     * 删除应用信息
     * @param appIds
     * @return
     */
    int delete(Set<String> appIds);


}
