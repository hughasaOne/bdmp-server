package com.rhy.bdmp.open.modules.assets.dao;

import com.rhy.bdmp.open.modules.assets.domain.bo.AppDirBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.vo.AppDirVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author weicaifu
 */
@Mapper
public interface AppDao {
    List<AppDirVo> getAppDir(@Param("appDirBo") AppDirBo appDirBo,
                             @Param("userPermissions") UserPermissions userPermissions);
}
