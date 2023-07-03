package com.rhy.bdmp.system.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppTypeVo;

import java.util.List;
import java.util.Set;

public interface AppTypeService {

    /**
     * 应用类别列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);

    /**
     * @Description: 删除应用类别
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void delete(Set<String> appTypeIds);

    /**
     * @Description: 新增应用类别
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    int create(AppType appType);

    /**
     * @Description: 修改应用类别
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    int update(AppType appType);

    /**
    * @Description: 查询应用类型子节点（根据应用类型ID）
    * @Author: dongyu
    * @Date: 2021/5/20
    */
    List<AppTypeVo> findAppTypeChildren(String parentId, String includeId, QueryVO queryVO);

    /**
     * 通过appTypeId向下查找包含的所有appTypeId及自身
     * @param appTypeId  应用类型ID
     * @param isSelf 是否包含自身
     * @return
     */
    Set<String> getAppTypeIdsByDown(String appTypeId, Boolean isSelf);
}
