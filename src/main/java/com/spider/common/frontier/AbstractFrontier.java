package com.spider.common.frontier;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 爬行者  bdb(Berkeley Database)数据库的简单存储，获取，删除
 * Created by liaoqiangang-pc on 2018/3/5.
 */
public abstract class AbstractFrontier {

    private Environment env;

    private static final String CLASS_CATALOG = "java_class_catalog";

    protected StoredClassCatalog javaCatalog;

    protected Database catalogdatabase;

    protected Database database;


    /**
     *  初始化数据库对象
     * @param homeDirectory 目录结构
     * @throws DatabaseException
     * @throws FileNotFoundException
     */
    public AbstractFrontier(String homeDirectory) throws DatabaseException, FileNotFoundException {
        // 打开 env
        System.out.println("Opening environment in: " + homeDirectory);
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);
        env = new Environment(new File(homeDirectory), envConfig);
        // 设置 DatabaseConfig
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setTransactional(true);
        dbConfig.setAllowCreate(true);
        // 打开
        catalogdatabase = env.openDatabase(null, CLASS_CATALOG, dbConfig);
        javaCatalog = new StoredClassCatalog(catalogdatabase);
        // 设置 DatabaseConfig
        DatabaseConfig dbConfig0 = new DatabaseConfig();
        dbConfig0.setTransactional(true);
        dbConfig0.setAllowCreate(true);
        // 打开
        database = env.openDatabase(null, "URL", dbConfig);
    }

    /**
     * 关闭数据库，关闭环境
      * @throws DatabaseException
     */
    public void close() throws DatabaseException {
        database.close();
        javaCatalog.close();
        env.close();
    }

    /**
     * put 方法
     * @param key
     * @param value
     */
    protected abstract void put(Object key,Object value);

    /**
     * get 方法
     * @param key
     * @return
     */
    protected abstract Object get(Object key);

    /**
     * delete 方法
     * @param key
     * @return
     */
    protected abstract Object delete(Object key);
}

