package com.temporary.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.temporary.bean.DaoMaster;
import com.temporary.bean.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Dee on 2018/8/9.
 */

public class DBManager {
    public final static String DB_NAME = "searcher_history";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private Context mContext;

    public DBManager(Context context) {
        this.mContext = context;
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
    }


    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mDevOpenHelper == null) {
            mDevOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
        }
        SQLiteDatabase database = mDevOpenHelper.getWritableDatabase();
        return database;
    }

    /**
     * 添加一条记录
     *
     * @param searcherHistory
     */
    public void insertHistory(SearcherHistory searcherHistory) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        searcherHistoryDao.insert(searcherHistory);
    }

    /**
     * 添加记录集合
     *
     * @param searcherHistories
     */
    public void insertHistoryList(List<SearcherHistory> searcherHistories) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        searcherHistoryDao.insertInTx(searcherHistories);
    }

    /**
     * 删除记录
     *
     * @param searcherHistory
     */
    public void deleteHistory(SearcherHistory searcherHistory) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        searcherHistoryDao.delete(searcherHistory);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllHistory() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        searcherHistoryDao.deleteAll();
    }

    /**
     * 修改一条记录
     *
     * @param searcherHistory
     */
    public void updateHistory(SearcherHistory searcherHistory) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        searcherHistoryDao.update(searcherHistory);
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<SearcherHistory> queryHistory() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        QueryBuilder<SearcherHistory> queryBuilder = searcherHistoryDao.queryBuilder();
        List<SearcherHistory> searcherHistories = queryBuilder.list();
        return searcherHistories;
    }

    /**
     * 查询历史为 history 的集合
     *
     * @param history
     * @return
     */
    public List<SearcherHistory> queryHistoryByHistory(String history) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        QueryBuilder<SearcherHistory> builder = searcherHistoryDao.queryBuilder();
        builder.where(SearcherHistoryDao.Properties.MHistory.eq(history));
        return builder.list();
    }

    /**
     * 判断内容为 history 是否已存在于数据库中
     *
     * @param history
     * @return
     */
    public boolean isHistoryExist(String history) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearcherHistoryDao searcherHistoryDao = daoSession.getSearcherHistoryDao();
        QueryBuilder<SearcherHistory> builder = searcherHistoryDao.queryBuilder();
        builder.where(SearcherHistoryDao.Properties.MHistory.eq(history));
        return builder.list().size() == 0 ? false : true;
    }
}
