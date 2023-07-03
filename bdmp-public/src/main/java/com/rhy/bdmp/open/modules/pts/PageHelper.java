package com.rhy.bdmp.open.modules.pts;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: yanggj
 * @Description: 分页参数处理工具类
 * @Date: 2021/12/16 16:01
 * @Version: 1.0.0
 */
public class PageHelper {

    public static <T> Page<T> buildPage(Integer currentPage, Integer size) {
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        Page<T> page = new Page<>(currentPage, size);
        page.setCurrent(currentPage);
        page.setSize(size);
        return page;
    }

}
