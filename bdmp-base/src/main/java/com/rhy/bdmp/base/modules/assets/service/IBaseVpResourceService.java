package com.rhy.bdmp.base.modules.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.VpResource;

import java.util.List;
import java.util.Set;

/**
 * @description 可视对讲资源表 服务接口
 * @author weicaifu
 * @date 2022-10-12 11:19
 * @version V1.0
 **/
public interface IBaseVpResourceService extends IService<VpResource> {

    /**
     * 可视对讲资源表列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<VpResource> list(QueryVO queryVO);

    /**
     * 可视对讲资源表列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<VpResource> page(QueryVO queryVO);

    /**
     * 查看可视对讲资源表(根据ID)
     * @param id
     * @return
     */
    VpResource detail(String id);

    /**
     * 新增可视对讲资源表
     * @param vpResource
     * @return
     */
    int create(VpResource vpResource);

    /**
     * 修改可视对讲资源表
     * @param vpResource
     * @return
     */
    int update(VpResource vpResource);

    /**
     * 删除可视对讲资源表
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
