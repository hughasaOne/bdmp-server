package com.rhy.bdmp.system.modules.assets.dao;

import com.rhy.bdmp.system.modules.assets.domain.vo.BoxVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 终端箱数据持久化层
 * @author 魏财富
 */
@Mapper
public interface DeviceBoxDao {
    /**
     * 查询出基础数据平台没有的box，做新增
     */
    List<BoxVo> getDiffBox();

    /**
     * 查询出基础数据平台和box系统都有的box，做更新
     */
    List<BoxVo> getSameBox();
}
