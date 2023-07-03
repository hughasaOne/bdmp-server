package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.DictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface DictDao extends BaseMapper<Dict> {

    Page<Dict> queryPage(Page<Dict> page, @Param("parentCode") String parentCode, @Param("dictName") String dictName);

    List<Dict> queryByCode(@Param("parentCode") String parentCode, @Param("dictName") String dictName);

    List<DictVO> findChild(@Param("parentId") String parentId, @Param("includeId")String includeId, @Param("ew") QueryWrapper<DictVO> queryWrapper);

    List<DictVO> findChildDict(@Param("parentId") String parentId, @Param("ew") QueryWrapper<DictVO> queryWrapper);
}
