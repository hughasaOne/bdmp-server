package com.rhy.bdmp.open.modules.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * 视频资源 数据操作接口
 * @author weicaifu
 **/
@Mapper
public interface VideoDao extends BaseMapper<Video> {

}
