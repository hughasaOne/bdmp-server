package com.rhy.bdmp.open.modules.pts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.enums.FacilitiesTypeEnum;
import com.rhy.bdmp.open.modules.pts.PageHelper;
import com.rhy.bdmp.open.modules.pts.dao.PtsAssetsDao;
import com.rhy.bdmp.open.modules.pts.service.IPtsAssetsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: yanggj
 * @Date: 2021/12/15 14:44
 * @Version: 1.0.0
 */
@Service
public class PtsAssetsServiceImpl implements IPtsAssetsService {

    private final PtsAssetsDao ptsAssetsDao;

    public PtsAssetsServiceImpl(PtsAssetsDao ptsAssetsDao) {
        this.ptsAssetsDao = ptsAssetsDao;
    }

    @Override
    public Page<?> queryPageWaySection(Integer currentPage, Integer size) {
        Page<HashMap<String, Object>> page= PageHelper.buildPage(currentPage,size);
        return ptsAssetsDao.queryPageWaySection(page);
    }

    @Override
    public Page<?> queryPageServiceArea(Integer currentPage, Integer size) {
        Page<HashMap<String, Object>> page= PageHelper.buildPage(currentPage,size);
        return ptsAssetsDao.queryPageServiceArea(page, FacilitiesTypeEnum.SERVICE_AREA.getCode());
    }

    @Override
    public Page<?> queryPageTollStation(Integer currentPage, Integer size) {
        Page<HashMap<String, Object>> page= PageHelper.buildPage(currentPage,size);
        return ptsAssetsDao.queryPageTollStation(page, FacilitiesTypeEnum.TOLL_STATION.getCode());
    }

}
