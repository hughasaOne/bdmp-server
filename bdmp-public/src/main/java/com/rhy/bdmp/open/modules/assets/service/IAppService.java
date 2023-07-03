package com.rhy.bdmp.open.modules.assets.service;

import com.rhy.bdmp.open.modules.assets.domain.bo.AppDirBo;
import com.rhy.bdmp.open.modules.assets.domain.vo.AppDirVo;

import java.util.List;

/**
 * 应用目录服务
 * @author weicaifu
 */
public interface IAppService {
    List<AppDirVo> getAppDir(AppDirBo appDirBo);
}
