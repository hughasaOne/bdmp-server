package com.rhy.bdmp.portal.modules.waitdo.service;

import com.rhy.bdmp.portal.modules.waitdo.domain.vo.WaitDo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 门户待办
 * @author lipeng
 * @date 2021-01-21 11:09
 * @version V1.0
 **/
public interface IWaitDoService {

    Map<String, List<WaitDo>> findWaitDoList(Set<String> appIds);

}
