package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 用户映射 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseUserMappingService extends IService<UserMapping> {

    /**
     * 用户映射列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<UserMapping> list(QueryVO queryVO);

    /**
     * 用户映射列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<UserMapping> page(QueryVO queryVO);

    /**
     * 查看用户映射(根据ID)
     * @param userMappingId
     * @return
     */
    UserMapping detail(String userMappingId);

    /**
     * 新增用户映射
     * @param userMapping
     * @return
     */
    int create(UserMapping userMapping);

    /**
     * 修改用户映射
     * @param userMapping
     * @return
     */
    int update(UserMapping userMapping);

    /**
     * 删除用户映射
     * @param userMappingIds
     * @return
     */
    int delete(Set<String> userMappingIds);


}
