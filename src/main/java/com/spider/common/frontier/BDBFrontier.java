package com.spider.common.frontier;

import com.alibaba.fastjson.JSONObject;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseException;
import com.spider.entity.CrawlUrl;

import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by liaoqiangang-pc on 2018/3/6.
 */
public class BDBFrontier extends AbstractFrontier implements Frontier {

    private StoredMap pendingUrisDB = null;

    /**
     * 使用默认的路径和缓存大小构造函数
     *
     * @param homeDirectory
     * @throws DatabaseException
     * @throws FileNotFoundException
     */
    public BDBFrontier(String homeDirectory) throws DatabaseException, FileNotFoundException {
        super(homeDirectory);
        EntryBinding keyBinding = new SerialBinding(javaCatalog, String.class);
        EntryBinding valueBinding = new SerialBinding(javaCatalog, CrawlUrl.class);
        pendingUrisDB = new StoredMap(database, keyBinding, valueBinding, true);
    }


    /**
     * //获得下一条记录
     *
     * @return
     * @throws Exception
     */
    @Override
    public CrawlUrl getNext() throws Exception {
        CrawlUrl result = null;
        if (!pendingUrisDB.isEmpty()) {
            Set entrys = pendingUrisDB.entrySet();
            System.out.println(entrys);
            Entry<String, CrawlUrl> entry = (Entry<String, CrawlUrl>) pendingUrisDB.entrySet().iterator().next();
            result = entry.getValue();
            System.out.println("key：" +entry.getKey()+"\tvalue："+ JSONObject.toJSONString(entry.getValue()));
            //delete(entry.getKey());
        }
        return result;
    }

    /**
     * 存入url
     *
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    public boolean putUrl(CrawlUrl url) throws Exception {
        put(url.getOriUrl(), url);
        return true;

    }

    /**
     * 存入数据库
     *
     * @param key
     * @param value
     */
    @Override
    protected void put(Object key, Object value) {
        pendingUrisDB.put(key, value);
    }

    /**
     * 取出
     *
     * @param key
     * @return
     */
    @Override
    protected Object get(Object key) {
        return pendingUrisDB.get(key);
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    @Override
    protected Object delete(Object key) {
        return pendingUrisDB.remove(key);
    }


    /**
     * 获取
     *
     * @return
     */
    private int getSize() {
        return pendingUrisDB.size();
    }

    private void printAll() {
        CrawlUrl result = null;
        if (!pendingUrisDB.isEmpty()) {
            Set entrys = pendingUrisDB.entrySet();
            while (entrys.iterator().hasNext()) {
                Entry<String, CrawlUrl> entry = (Entry<String, CrawlUrl>) entrys.iterator().next();
                System.out.println("key：" + entry.getKey() + "\tvalue：" + entry.getValue().getOriUrl());
                pendingUrisDB.remove(entry.getKey(),entry.getValue());
            }
        }else{
            System.out.println("no data……");
        }
    }


    /**
     * 根据 URL 计算键值，可以使用各种压缩算法，包括 MD5 等压缩算法
     *
     * @param url
     * @return
     */
    private String caculateUrl(String url) {
        return url;
    }


    // 测试函数
    public static void main(String[] strs) {
        try {
            String keyUrl = "http://liaoqiangang.com";
            BDBFrontier bBDBFrontier = new BDBFrontier("D:\\bdb");
            //存入数据入库
            CrawlUrl url = new CrawlUrl();
            url.setOriUrl(keyUrl);
            bBDBFrontier.putUrl(url);
            //获取数据
            CrawlUrl crawlUrl =  bBDBFrontier.getNext();
            if(crawlUrl!=null){
                System.out.println(crawlUrl.getOriUrl());
            }
            //删除数据
//            bBDBFrontier.delete(keyUrl);
            //获取数据打印数据
//            bBDBFrontier.printAll();
            //关闭数据库连接
//            bBDBFrontier.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


}
