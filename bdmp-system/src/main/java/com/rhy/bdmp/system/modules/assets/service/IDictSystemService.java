package com.rhy.bdmp.system.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;

import java.util.List;
import java.util.Set;

/**
 * Created on 2021/11/1.
 *
 * @author duke
 */
public interface IDictSystemService {

    /**
     * 创建系统字典
     * @param dictSystem 系统字典实体
     * @return int
     */
    boolean create(DictSystem dictSystem);

    /**
     * 修改系统字典
     * @param dictSystem 系统字典实体
     * @return int
     */
    boolean update(DictSystem dictSystem);

    /**
     * 删除系统字典
     * @param systemIds 系统id
     * @return int
     */
    int delete(Set<String> systemIds);

    /**
     * 系统字典详情
     * @param systemId 系统字典实体
     * @return DictSystem
     */
    DictSystem detail(String systemId);

    /**
     * 分页查询 系统字典
     * @param currentPage 当前页码
     * @param size        每一页显示数量
     * @param param       搜索条件
     * @return    结果集
     */
    Page<DictSystem> queryPage(Integer currentPage, Integer size, String param);

    /**
     * 查询所有系统字典
     * @param param 搜索条件
     * @return 结果集
     */
    List<DictSystem> list(String param);

    Object getDictSystem(DictBO dictBO);
}
