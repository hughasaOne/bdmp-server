package com.rhy.bdmp.quartz.modules.sysjob.domain.bo;

import lombok.Data;

/**
 * @author shuaichao
 * @create 2022-03-17 17:34
 */
@Data
public class JobResponse {
    public static  String STATUS_SUCCESS="1";
    public static  String STATUS_FAIL="2";
    public static  String STATUS_TIME_OUT="3";
    private String status;
    private String result;
}
