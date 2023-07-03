package com.rhy.bdmp.collect.modules.camera.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.collect.modules.camera.domain.po.Sy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 视频资源(上云) 数据操作接口
 * @author weicaifu
 * @date 2022-06-07 11:49
 * @version V1.0
 **/
@Mapper
public interface BaseSyDao extends BaseMapper<Sy> {

}
