package com.rhy.bdmp.open.modules.pts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: yanggj
 * @Date: 2021/12/15 14:43
 * @Version: 1.0.0
 */
public interface IPtsAssetsService {
    Page<?> queryPageWaySection(Integer currentPage, Integer size);

    Page<?> queryPageServiceArea(Integer currentPage, Integer size);

    Page<?> queryPageTollStation(Integer currentPage, Integer size);
}
