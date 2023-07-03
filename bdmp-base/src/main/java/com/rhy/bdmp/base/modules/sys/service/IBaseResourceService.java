package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 资源 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseResourceService extends IService<Resource> {

    /**
     * 资源列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Resource> list(QueryVO queryVO);

    /**
     * 资源列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Resource> page(QueryVO queryVO);

    /**
     * 查看资源(根据ID)
     * @param resourceId
     * @return
     */
    Resource detail(String resourceId);

    /**
     * 新增资源
     * @param resource
     * @return
     */
    int create(Resource resource);

    /**
     * 修改资源
     * @param resource
     * @return
     */
    int update(Resource resource);

    /**
     * 删除资源
     * @param resourceIds
     * @return
     */
    int delete(Set<String> resourceIds);


}
