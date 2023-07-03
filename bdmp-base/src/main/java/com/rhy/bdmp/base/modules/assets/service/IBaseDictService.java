package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 台账字典 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseDictService extends IService<Dict> {

    /**
     * 台账字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Dict> list(QueryVO queryVO);

    /**
     * 台账字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Dict> page(QueryVO queryVO);

    /**
     * 查看台账字典(根据ID)
     * @param dictId
     * @return
     */
    Dict detail(String dictId);

    /**
     * 新增台账字典
     * @param dict
     * @return
     */
    int create(Dict dict);

    /**
     * 修改台账字典
     * @param dict
     * @return
     */
    int update(Dict dict);

    /**
     * 删除台账字典
     * @param dictIds
     * @return
     */
    int delete(Set<String> dictIds);


}
