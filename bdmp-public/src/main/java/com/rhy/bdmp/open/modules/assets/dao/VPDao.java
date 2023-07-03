package com.rhy.bdmp.open.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.open.modules.assets.domain.po.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VPDao extends BaseMapper<Resource> {
    List<Resource> getContactTree(@Param("orgId") String orgId, @Param("nodeType") String nodeType, @Param("name") String name);

    List<Resource> getTerminalByOrg(@Param("resource") Resource resource,@Param("nodeType") String nodeType);

    List<Resource> findChildren(@Param("ids") List<String> ids);

    List<Resource> findParent(@Param("rootList") List<Resource> rootList);

    List<Resource> findPCSeat(@Param("resource") Resource resource);
}
