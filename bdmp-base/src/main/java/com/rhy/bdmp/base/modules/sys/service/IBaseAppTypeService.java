package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 应用类别 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseAppTypeService extends IService<AppType> {

    /**
     * 应用类别列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<AppType> list(QueryVO queryVO);

    /**
     * 应用类别列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<AppType> page(QueryVO queryVO);

    /**
     * 查看应用类别(根据ID)
     * @param appTypeId
     * @return
     */
    AppType detail(String appTypeId);

    /**
     * 新增应用类别
     * @param appType
     * @return
     */
    int create(AppType appType);

    /**
     * 修改应用类别
     * @param appType
     * @return
     */
    int update(AppType appType);

    /**
     * 删除应用类别
     * @param appTypeIds
     * @return
     */
    int delete(Set<String> appTypeIds);


}
