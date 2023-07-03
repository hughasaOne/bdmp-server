package com.rhy.bdmp.system.modules.sys.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.WayMapping;
import com.rhy.bdmp.system.modules.sys.domain.vo.CCPWayVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.OrgVo;

import java.util.List;
import java.util.Set;

public interface OrgService {
    /**
    * @Description: 删除
    * @Author: dongyu
    * @Date: 2021/4/14
    */
    void delete(Set<String> orgIds);

    /**
    * @Description: 查询组织关系
    * @Author: dongyu
    * @Date: 2021/4/15
     */
    List<NodeVo> findOrgTree();

    /**
    * @Description: 新增组织
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int create(Org org);

    /**
    * @Description: 修改组织
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int update(Org org);

    /**
     * 组织机构列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);

    /**
     * 查看组织机构(根据ID)
     * @param orgId
     * @return
     */
    OrgVo detail(String orgId);

    /**
     * 通过orgId向下查找包含的所有orgId及自身
     * @param orgId  机构ID
     * @param isSelf 是否包含自身
     * @return
     */
    Set<String> getOrgIdsByDown(String orgId, Boolean isSelf);

    /**
     * 根据父节点获取机构列表
     * @param queryVO 查询条件
     * @return
     */
    List<OrgVo> listByParentId(QueryVO queryVO);

    /**
     * 查询组织:根据ID获取同级与上级数据
     * @param ids       组织ID字符串,多个英文逗号分隔
     * @param hasSelf   是否包含参数ID节点
     * @return
     */
    List<Tree<String>> getSuperior(String ids, Boolean hasSelf);

    List<CCPWayVo> getCCPWay(String orgId);

    List<CCPWayVo> getCCPWayMapping(String orgId);

    void ccpWayMappingSave(List<WayMapping> wayMappingList,String orgId);

}
