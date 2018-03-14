package com.spider.common.frontier;

import com.spider.entity.CrawlUrl;

/**
 * 爬行者 抽奖接口
 * Created by liaoqiangang-pc on 2018/3/5.
 */
public interface Frontier {

    /**
     * 获取下一个CrawlUrl链接对象
     * @return
     * @throws Exception
     */
    public CrawlUrl getNext()throws Exception;

    /**
     * 存入CrawlUrl链接对象
     * @param url
     * @return
     * @throws Exception
     */
    public boolean putUrl(CrawlUrl url) throws Exception;

    //public boolean visited(CrawlUrl url);
}
