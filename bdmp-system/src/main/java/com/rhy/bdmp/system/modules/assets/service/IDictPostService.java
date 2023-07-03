package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;

/**
 * 岗位字典管理
 * @author weicaifu
 **/
public interface IDictPostService {
    /**
     * 运营公司岗位字典树
     */
    Object getOrgPostTree(String parentCode);

    Object getDictPost(DictBO dictBO);

    Object getOrgPost(String orgType);
}
