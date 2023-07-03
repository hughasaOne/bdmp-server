package com.rhy.bdmp.open.modules.assets.domain.bo;

import lombok.Data;

/**
 * Created on 2021/11/12.
 *
 * @author duke
 */
@Data
public class DeviceStatisticsInfo {
    /**
     * 状态 0 正常  1异常
     */
    private String status;

    /**
     * 数量
     */
    private int total;

}
