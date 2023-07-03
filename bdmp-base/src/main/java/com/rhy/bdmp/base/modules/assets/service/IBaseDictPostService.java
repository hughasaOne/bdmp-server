package com.rhy.bdmp.base.modules.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.DictPost;

import java.util.List;
import java.util.Set;

/**
 * @description 岗位字典 服务接口
 * @author weicaifu
 * @date 2022-03-16 09:22
 * @version V1.0
 **/
public interface IBaseDictPostService extends IService<DictPost> {

    /**
     * 岗位字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DictPost> list(QueryVO queryVO);

    /**
     * 岗位字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DictPost> page(QueryVO queryVO);

    /**
     * 查看岗位字典(根据ID)
     * @param postId
     * @return
     */
    DictPost detail(String postId);

    /**
     * 新增岗位字典
     * @param dictPost
     * @return
     */
    int create(DictPost dictPost);

    /**
     * 修改岗位字典
     * @param dictPost
     * @return
     */
    int update(DictPost dictPost);

    /**
     * 删除岗位字典
     * @param postIds
     * @return
     */
    int delete(Set<String> postIds);


}
