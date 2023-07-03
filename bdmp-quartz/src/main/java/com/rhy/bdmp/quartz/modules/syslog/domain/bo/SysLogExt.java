package com.rhy.bdmp.quartz.modules.syslog.domain.bo;

import com.rhy.bdmp.base.modules.sys.domain.po.SysLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shuaichao
 * @create 2022-03-15 14:00
 */
@Data
public class SysLogExt extends SysLog implements Serializable {
    private String jobName ;
    private String description;
    private String dirId;
}
