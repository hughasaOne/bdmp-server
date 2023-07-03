package com.rhy.bdmp.open.modules.org.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.common.domain.vo.TreeNode;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgDetailBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgListBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgTreeBo;
import com.rhy.bdmp.open.modules.org.domain.dto.IPTelDto;
import com.rhy.bdmp.open.modules.org.domain.vo.BaseOrgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "orgDaoV1")
public interface OrgDao extends BaseMapper<Org> {

    /**
     * 获取运营集团
     */
    List<TreeNode> getGroup(@Param("userPermissions") UserPermissions userPermissions);

    /**
     * 获取运营公司
     */
    List<TreeNode> getOrg(@Param("userPermissions") UserPermissions userPermissions);

    List<TreeNode> getWay(@Param("userPermissions") UserPermissions userPermissions,
                          @Param("orgNodeList") List<TreeNode> orgNodeList);

    List<TreeNode> getFac(@Param("userPermissions") UserPermissions userPermissions,
                          @Param("wayNodeList") List<TreeNode> wayNodeList);

    /**
     * 获取所有组织机构
     */
    List<TreeNode> getAllOrg(@Param("userPermissions") UserPermissions userPermissions,
                             @Param("orgTreeBo") OrgTreeBo orgTreeBo);

    List<TreeNode> getChildren(@Param("ids") List<String> ids);

    List<TreeNode> getNodeById(@Param("orgTreeBo") OrgTreeBo orgTreeBo,
                               @Param("userPermissions") UserPermissions userPermissions,
                               @Param("nodeIds") String[] nodeIds);

    List<TreeNode> getParent(@Param("ids") List<String> ids);

    List<BaseOrgVo> getOrgList(@Param("orgListBo") OrgListBo orgListBo,
                               @Param("userPermissions") UserPermissions userPermissions);

    Page<BaseOrgVo> getOrgPage(@Param("baseOrgVoPage") Page<BaseOrgVo> baseOrgVoPage,
                                @Param("orgListBo") OrgListBo orgListBo,
                               @Param("userPermissions") UserPermissions userPermissions);

    BaseOrgVo getOrgById(@Param("detailBo") OrgDetailBo detailBo);

    /**
     * 获取ip电话关系列表
     */
    List<IPTelDto> getIPTelRelationList(@Param("orgId") String orgId);
}
