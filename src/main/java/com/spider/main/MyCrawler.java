package com.spider.main;

import com.spider.filter.LinkFilter;
import com.spider.queue.LinkQueue;
import com.spider.utils.DownLoadFile;
import com.spider.utils.HtmlParserTool;

import java.util.Set;

/**
 *  我的爬虫 :实现url 的解析，逐层抓取  下载url 页面
 * Created by liaoqiangang-pc on 2018/3/5.
 */
public class MyCrawler {

    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            LinkQueue.addUnvisitedUrl(seeds[i]);
        }
    }


    /**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) {
        //定义过滤器，提取以 http://www.lietu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.indexOf("www.runoob.com")>0) {
//                if (url.startsWith("c.runoob.com")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!LinkQueue.unVisitedUrlsEmpty() && LinkQueue.getVisitedUrlNum() <= 1000) {
            //队头 URL 出队列
            String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            if (visitUrl == null){
                continue;
            }
            DownLoadFile downLoader = new DownLoadFile();
            //下载网页
            downLoader.downloadFile(visitUrl);
            //该 URL 放入已访问的 URL 中
            LinkQueue.addVisitedUrl(visitUrl);
            //提取出下载网页中的 URL
            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
//            Set<String> links = HtmlParserTool.extracLinks(visitUrl, new LinkFilter());
            //新的未访问的 URL 入队
            for (String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }

    //main 方法入口
    public static void main(String[]args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"http://www.runoob.com/"});
    }


}
