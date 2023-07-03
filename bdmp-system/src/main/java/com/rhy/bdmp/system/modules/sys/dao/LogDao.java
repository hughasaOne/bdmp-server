package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.sys.domain.bo.LogBo;
import com.rhy.bdmp.system.modules.sys.domain.vo.LogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component("logDao1")
public interface LogDao {

    Page<LogVo> page(Page<LogVo> page, @Param("logBo") LogBo logBo);
}
