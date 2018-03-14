package com.spider.common.bdb;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.util.RuntimeExceptionWrapper;

import java.util.logging.Level;

/**
 * Created by liaoqiangang-pc on 2018/3/14.
 */
public class BdbMultipleWorkQueues {

    // 存放所有待处理的 URL 的数据库
    private Database pendingUrisDB = null;

    // 由 key 获取一个链接
    public CrawlURI get(DatabaseEntry headKey) throws DatabaseException {
        DatabaseEntry result = new DatabaseEntry();
        // 由 key 获取相应的链接
        OperationStatus status = getNextNearestItem(headKey, result);
        CrawlURI retVal = null;
        if (status != OperationStatus.SUCCESS) {
            LOGGER.severe("See '1219854 NPE je-2.0 " + "entryToObject '. OperationStatus " + " was not SUCCESS: " + status + ", headKey " + BdbWorkQueue.getPrefixClassKey(headKey.getData()));
            return null;
        }
        try {
            retVal = (CrawlURI) crawlUriBinding.entryToObject(result);
        } catch (RuntimeExceptionWrapper rw) {
            LOGGER.log(Level.SEVERE, "expected object missing in queue "
                    + BdbWorkQueue.getPrefixClassKey(headKey.getData()), rw);
            return null;
        }
        retVal.setHolderKey(headKey);
        return retVal;
        // 返回链接
    }

    // 从等待处理列表中获取一个链接
    protected OperationStatus getNextNearestItem(DatabaseEntry headKey, DatabaseEntry result) throws DatabaseException {
        Cursor cursor = null;
        OperationStatus status;
        try {
            // 打开游标
            cursor = this.pendingUrisDB.openCursor(null, null);
            status = cursor.getSearchKey(headKey, result, null);
            if (status != OperationStatus.SUCCESS || result.getData().length > 0) {
                throw new DatabaseException("bdb queue cap missing");
            }
            status = cursor.getNext(headKey, result, null);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return status;
    }

    /**
     * 添加 URL 到数据库
     */
    public void put(CrawlURI curi, boolean overwriteIfPresent) throws DatabaseException {
        DatabaseEntry insertKey = (DatabaseEntry) curi.getHolderKey();

        if (insertKey == null) {
            insertKey = calculateInsertKey(curi);
            curi.setHolderKey(insertKey);
        }
        DatabaseEntry value = new DatabaseEntry();
        crawlUriBinding.objectToEntry(curi, value);
        if (LOGGER.isLoggable(Level.FINE)) {
            tallyAverageEntrySize(curi, value);
        }
        OperationStatus status;
        if (overwriteIfPresent) {
            // 添加
            status = pendingUrisDB.put(null, insertKey, value);
        } else {
            status = pendingUrisDB.putNoOverwrite(null, insertKey, value);
        }
        if (status != OperationStatus.SUCCESS) {
            LOGGER.severe("failed; " + status + " " + curi);
        }
    }
}
