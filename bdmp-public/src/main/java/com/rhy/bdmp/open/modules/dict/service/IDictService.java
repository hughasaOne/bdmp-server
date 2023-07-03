package com.rhy.bdmp.open.modules.dict.service;

import com.rhy.bdmp.open.modules.dict.domain.bo.DeviceDictBo;
import com.rhy.bdmp.open.modules.dict.domain.bo.DictBo;

public interface IDictService {
    Object getSystemTree();

    Object getDeviceTree(DeviceDictBo deviceDictBo);

    Object getDeviceDictList(String deviceType);

    Object getDictTree(DictBo dictBo);

    Object getDictDetail(String code);

}
