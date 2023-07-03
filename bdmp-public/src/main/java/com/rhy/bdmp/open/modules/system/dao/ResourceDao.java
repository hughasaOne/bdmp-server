package com.rhy.bdmp.open.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.rhy.bdmp.open.modules.system.domain.vo.ResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceDao extends BaseMapper<Resource> {

    /**
     * 查询当前用户拥有的目录和菜单、按钮资源
     * @param userId 用户id
     * @param appId 应用id
     * @return 当前用户拥有的目录和菜单、按钮资源 集合
     */
    List<ResourceVo> selectResourceByTypeAndRole2(@Param("userId") String userId, @Param("appId") String appId);
}
