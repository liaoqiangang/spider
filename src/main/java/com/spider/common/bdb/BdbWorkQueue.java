package com.spider.common.bdb;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;

import java.io.IOException;

/**
 * Created by liaoqiangang-pc on 2018/3/14.
 */
public class BdbWorkQueue extends WorkQueue implements Comparable, Serializabl {
    //获取一个 URL
    protected CrawlURI peekItem(final WorkQueueFrontier frontier) throws IOException {
        // 关键:从 BdbFrontier 中返回
        pendingUris final BdbMultipleWorkQueues queues = ((BdbFrontier) frontier).getWorkQueues();
        DatabaseEntry key = new DatabaseEntry(origin);
        CrawlURI curi = null;
        int tries = 1;
        while (true) {
            try {
                //获取链接
                curi = queues.get(key);
            } catch (DatabaseException e) {
                LOGGER.log(Level.SEVERE, "peekItem failure; retrying", e);
            }
            return curi;
        }
    }
}
