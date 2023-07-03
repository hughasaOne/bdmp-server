package com.rhy.bdmp.open.modules.fac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.fac.domain.vo.BaseFacVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "FacDaoV1")
public interface FacDao extends BaseMapper<Facilities> {

    List<BaseFacVo> getFacList(@Param("commonBo") CommonBo commonBo,
                               @Param("userPermissions") UserPermissions userPermissions);
}
