package com.rhy.bdmp.quartz.modules.syslog.task;

import com.rhy.bdmp.quartz.modules.syslog.dao.SysLogDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时清理日志
 * @author weicaifu
 */
/*@Component
@Slf4j
public class LogTask{
    @Resource
    private SysLogDao sysLogDao;

    @Value("${logs_time_length}")
    private int logsTimeLength;

    @Scheduled(cron = "0 59 23 * * ?")
    public void cleanLogByDate() {
        String timed = LocalDateTime.now().minusDays(logsTimeLength).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("开始清理 " + timed + " 之前的数据");
        int result = sysLogDao.cleanLogByDate(timed);
        log.info("清理结束 共清理 "+ result + " 条日志");
    }
}*/
