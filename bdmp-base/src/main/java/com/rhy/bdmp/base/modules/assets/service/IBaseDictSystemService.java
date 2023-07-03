package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 系统字典 服务接口
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
public interface IBaseDictSystemService extends IService<DictSystem> {

    /**
     * 系统字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DictSystem> list(QueryVO queryVO);

    /**
     * 系统字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DictSystem> page(QueryVO queryVO);

    /**
     * 查看系统字典(根据ID)
     * @param systemId
     * @return
     */
    DictSystem detail(String systemId);

    /**
     * 新增系统字典
     * @param dictSystem
     * @return
     */
    int create(DictSystem dictSystem);

    /**
     * 修改系统字典
     * @param dictSystem
     * @return
     */
    int update(DictSystem dictSystem);

    /**
     * 删除系统字典
     * @param systemIds
     * @return
     */
    int delete(Set<String> systemIds);


}
