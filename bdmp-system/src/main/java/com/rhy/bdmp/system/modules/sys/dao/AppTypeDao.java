package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppTypeDao extends BaseMapper<AppType> {

    /**
     * @Description: 查找应用类别的子类别（不包含当前节点）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findAppTypeChildrenIds(@Param("appTypeId") String appTypeId);

    /**
    * @Description: 获取应用类别及其父类别
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<AppTypeVo> findAppTypesParent(@Param("appTypeId") String appTypeId);

    /**
     * @Description: 查询应用类型子节点（根据应用类型ID）
     * @Author: dongyu
     * @Date: 2021/5/20
     */
    List<AppTypeVo> findAppTypeChildren(@Param("parentId") String parentId, @Param("includeId") String includeId, @Param("ew") QueryWrapper<AppTypeVo> queryWrapper);

}
