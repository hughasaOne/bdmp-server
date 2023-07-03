package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.sys.domain.vo.CCPWayVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.OrgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgDao extends BaseMapper<Org> {

    /**
     * @Description: 查找组织及其子组织集合
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    List<OrgVo> findOrgChildren(@Param("orgId") String orgId);

    /**
     * @Description: 查找组织及其父组织集合
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    List<OrgVo> findOrgParent(@Param("orgId") String orgId);


    /**
     * 获取机构列表(并判断是否含有孩子节点)
     * @param queryWrapper 查询条件
     * @return
     */
    List<OrgVo> listHasChildByQueryWraper(@Param("ew") QueryWrapper<Org> queryWrapper);

    List<CCPWayVo> getCCPWayList(@Param("orgId") String orgId);

    List<CCPWayVo> getCCPWayMapping(@Param("orgId") String orgId);
}
