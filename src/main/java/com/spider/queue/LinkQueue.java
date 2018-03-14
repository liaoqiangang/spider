package com.spider.queue;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * 集合  存储  已访问  和  待访问 的url
 * Created by liaoqiangang-pc on 2018/3/2.
 */
public class LinkQueue {

    //已访问的 url 集合
    private static Set visitedUrl = new HashSet();
    // 待访问的 url 集合
//    private static Queue unVisitedUrl = new Queue();
    private static Queue unVisitedUrl = new PriorityQueue();

    private static int i = 0;

    //获得 URL 队列
    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }

    // 添加到访问过的 URL 队列中
    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
        System.out.println("url："+url+"\tSize："+(i++));
    }

    /*// 移除访问过的 URL
    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }*/

    // 未访问的 URL 出队列
    public static Object unVisitedUrlDeQueue() {
        return unVisitedUrl.poll();
    }

    // 保证每个 URL 只被访问一次
    public static void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url))
            unVisitedUrl.add(url);
    }

    //获得已经访问的 URL 数目
    public static int getVisitedUrlNum() {
        return visitedUrl.size();
    }

    //判断未访问的 URL 队列中是否为空
    public static boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.isEmpty();
    }


}
