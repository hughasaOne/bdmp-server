package com.rhy.bdmp.portal.modules.newsnotice.service.impl;

import cn.hutool.core.date.DateUtil;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
import com.rhy.bdmp.portal.modules.newsnotice.dao.NewsNoticeDao;
import com.rhy.bdmp.portal.modules.newsnotice.service.NewsNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shuaichao
 * @create 2022-03-07 13:48
 */
@Service
@Slf4j
public class NewsNoticeServiceImpl  implements NewsNoticeService {

    @Resource
    private  NewsNoticeDao newsNoticeDao ;


    @Override
    public List<NewsNotice> list() {
       return newsNoticeDao.findByCurrentDate(DateUtil.format(new Date(),"yyyy-MM-dd"));
    }



    @Override
    public void deleteAndinsertByJsoup(List<NewsNotice> newsNotices) {
        //先删除当天插入的数据
        deleteByCreateDate(new Date());
        for(NewsNotice item : newsNotices){
            newsNoticeDao.insert(item);
        }
    }

    @DataSource("portal")
    public Long deleteByCreateDate(Date date){
        return newsNoticeDao.deleteByCreateDate(date);
    }

    @Override
    public Integer insertByJsoup() {
        List<NewsNotice> newsNotices =getByJsoup();
        deleteAndinsertByJsoup(newsNotices);
        return newsNotices.size();
    }

    /**
     * 爬虫获取交投网站新闻
     * @return
     */
    @Override
    public List<NewsNotice> getByJsoup() {
        //TODO
        List<NewsNotice> list = new ArrayList<NewsNotice>();
        try {
            String url = "";
            Document document = Jsoup.connect("http://www.hbjttz.com/jtxw.html").timeout(3000)
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                    .header("Upgrade-Insecure-Requests","1")
                    .get();
            String html = document.html();
            //获取新闻li标签
            Elements elements = document.select("div#column_list > ul > li");
            for (Element element:elements){
                // 获取<a>标签
                Elements a = element.select("a");
                //拼接链接地址(网站链接用的相对路径./xxx/xxx  去除前面的.)
                url = element.baseUri() +a.attr("href").substring(1);
                NewsNotice newsNotice = new NewsNotice();
                newsNotice.setUrl(url);
                newsNotice.setTitle(a.attr("title"));
                newsNotice.setCreateBy("system");
                newsNotice.setCreateTime(new Date());
//                System.out.println(element);
                list.add(newsNotice);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;

    }

    public  NewsNotice findById(String id){
        NewsNotice newsNotice = newsNoticeDao.selectById(id);
        return newsNotice;
    }

}
