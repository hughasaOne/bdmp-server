package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bdmp.open.modules.assets.domain.bo.ManufacturerFacBo;

import java.util.List;

/**
 * 厂商业务接口
 * @author weicaifu
 */
public interface IManufacturerService {

    List<Tree<String>> getGroupOrgManufacturerTree(String orgId, String nodeType,Boolean isAsync);

    List<Tree<String>> getGroupOrgWayManufacturerTree(String orgId, String nodeType, Boolean isAsync);

    List<Tree<String>> getFacManufacturer(ManufacturerFacBo manufacturerFacBo);

    List<Tree<String>> getSubFacManufacturerTree(ManufacturerFacBo manufacturerFacBo);
}
