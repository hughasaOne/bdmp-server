package com.rhy.bdmp.portal.modules.task;

import cn.hutool.http.HttpRequest;
import com.rhy.bdmp.portal.aop.annotation.RedisTryLock;
import com.rhy.bdmp.portal.modules.newsnotice.service.NewsNoticeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 功能 用于定时每天获取湖北交投 新闻信息
 * @author shuaichao
 * @create 2022-03-07 14:06
 */
@Component
//@ConditionalOnExpression("${schedule.enable}")
public class NewsNoticeTask {

    @Resource
    private NewsNoticeService newsNoticeService;
    /**
     * 每天凌晨定时爬取湖北交投新闻公告
     */
    @Scheduled(cron = "0 0 0 * * *")
    //@RedisTryLock(keyName = "Jsoup NewsNotice Task", expireTime = 10)
    public void newsNoticeTask(){

        newsNoticeService.insertByJsoup();

    }

    public static void main(String[] args) {
        String body = HttpRequest.post("http://www.hbjttz.com/xwzx/jtxw/").execute().body();

        System.out.println(body);
    }
}
