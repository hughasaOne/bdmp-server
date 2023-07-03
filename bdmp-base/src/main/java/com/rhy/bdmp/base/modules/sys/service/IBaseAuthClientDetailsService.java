package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description oauth2认证配置 服务接口
 * @author shuaichao
 * @date 2022-03-23 17:22
 * @version V1.0
 **/
public interface IBaseAuthClientDetailsService extends IService<AuthClientDetails> {

    /**
     * oauth2认证配置列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<AuthClientDetails> list(QueryVO queryVO);

    /**
     * oauth2认证配置列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<AuthClientDetails> page(QueryVO queryVO);

    /**
     * 查看oauth2认证配置(根据ID)
     * @param id
     * @return
     */
    AuthClientDetails detail(String id);

    /**
     * 新增oauth2认证配置
     * @param authClientDetails
     * @return
     */
    int create(AuthClientDetails authClientDetails);

    /**
     * 修改oauth2认证配置
     * @param authClientDetails
     * @return
     */
    int update(AuthClientDetails authClientDetails);

    /**
     * 删除oauth2认证配置
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
