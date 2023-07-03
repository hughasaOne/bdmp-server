package com.rhy.bdmp.system.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import com.rhy.bcp.common.util.QueryVO;

import java.util.Set;

public interface UserMappingService {
    /**
    * @Description: 新增用户映射
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int create(UserMapping userMapping);

    /**
    * @Description: 修改用户映射
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int update(UserMapping userMapping);

    /**
    * @Description: 删除用户映射
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    void delete(Set<String> userMappingIds);

    /**
     * 用户映射列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);
}
