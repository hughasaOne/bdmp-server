package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bdmp.open.modules.assets.domain.bo.UpdateVPStatusBo;

import java.util.List;

public interface IVPService {
    List<Tree<String>> getContactTree(String orgId, String nodeType, String name, String exclude);

    void updateVPStatus(List<UpdateVPStatusBo> updateVPStatusBos);
}
