package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 系统字典 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseDictService extends IService<Dict> {

    /**
     * 系统字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Dict> list(QueryVO queryVO);

    /**
     * 系统字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Dict> page(QueryVO queryVO);

    /**
     * 查看系统字典(根据ID)
     * @param dictId
     * @return
     */
    Dict detail(String dictId);

    /**
     * 新增系统字典
     * @param dict
     * @return
     */
    int create(Dict dict);

    /**
     * 修改系统字典
     * @param dict
     * @return
     */
    int update(Dict dict);

    /**
     * 删除系统字典
     * @param dictIds
     * @return
     */
    int delete(Set<String> dictIds);


}
