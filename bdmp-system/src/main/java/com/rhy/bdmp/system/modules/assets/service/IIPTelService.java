package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.VpResource;
import com.rhy.bdmp.system.modules.assets.domain.bo.IPTelListBo;

import java.util.Set;

public interface IIPTelService {
    Object getDir();

    Object getResourceList(IPTelListBo ipTelListBo);

    void add(VpResource vpResource);

    Object detail(String id);

    void delete(Set<String> ids);

    void update(VpResource vpResource);
}
