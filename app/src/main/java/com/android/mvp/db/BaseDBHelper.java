package com.android.mvp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Luhao
 * on 2016/1/6.
 * 数据库帮助类代理类
 */
public class BaseDBHelper extends OrmLiteSqliteOpenHelper {

    private static BaseDBHelper instance;

    public static synchronized BaseDBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (BaseDBHelper.class) {
                if (instance == null) {
                    instance = new BaseDBHelper(context);
                }
            }
        }
        return instance;
    }


    /**
     * 写入每一个将要生成数据库表的树体类
     */
    private List<Class> tables() {
        List<Class> tables = new ArrayList<>();
//        tables.add(UserInfoBean.class);
//        tables.add(IndexBean.class);
//        tables.add(IndexLunfanContentListBean.class);
//        tables.add(IndexMoudleContentListBean.class);
//        tables.add(IndexMoudleListBean.class);
//        tables.add(IndexNavListBean.class);
        //tables.add(ShopSpecListBean.class);
        //tables.add(GoodsListBean.class);
        return tables;
    }


    /**
     * 参数说明
     *
     * @param context 上下文
     *                2 数据库名称
     *                3 代理工厂，null
     *                4 数据库版本
     */
    private BaseDBHelper(Context context) {
        super(context, DBContact.DB_NAME, null, DBContact.DB_VERSION);
        addTables();
    }

    /**
     * 数据库实例
     */
    protected SQLiteDatabase sqLiteDatabase;

    /**
     * 连接源
     */
    //protected ConnectionSource connectionSource = getConnectionSource();

    /**
     * 创建数据库
     * 我们也可以在程序初始化的时候就创建所有的表结构
     *
     * @param sqLiteDatabase   数据库
     * @param connectionSource 连接源
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        this.sqLiteDatabase = sqLiteDatabase;

    }

    /**
     * 升级数据库
     * 我们也可以在程序初始化的时候来进行更新全部的表结构
     *
     * @param sqLiteDatabases  数据库
     * @param connectionSource 连接源
     * @param i                原来的版本
     * @param i1               升级的版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabases, ConnectionSource connectionSource, int i, int i1) {
        this.sqLiteDatabase = sqLiteDatabases;
        Observable
                .create(new Observable.OnSubscribe<Void>() {
                    @Override
                    public void call(Subscriber<? super Void> subscriber) {
                        try {
                            for (Class cla : tables()) {
                                int i = TableUtils.dropTable(getConnectionSource(), cla, true);
                            }
                            subscriber.onNext(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void avoid) {
                        //删除所有表后再重新创建
                        onCreate(sqLiteDatabase, getConnectionSource());
                        addTables();
                    }
                });
    }

    private void addTables() {
        Observable
                .create(new Observable.OnSubscribe<List<Class>>() {
                    @Override
                    public void call(Subscriber<? super List<Class>> subscriber) {
                        try {
                            List<Class> tables = tables();
                            for (Class cla : tables) {
                                //如果数据库没有表就创建
                               int i = TableUtils.createTableIfNotExists(getConnectionSource(), cla);
                            }
                            subscriber.onNext(tables);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Class>>() {
                    @Override
                    public void call(List<Class> tables) {
                        //所有表创建完则清空集合中的对象
                        tables.clear();
                    }
                });
    }

    /**
     * 修改数据库的表结构
     */
    public boolean updateTable(Class cla) {
        /**
         * 先删除表，然后再调用创建表
         * 1，连接源
         * 2，表信息
         * 3，是否忽略错误
         */
        try {
            TableUtils.dropTable(getConnectionSource(), cla, true);
            return TableUtils.createTable(getConnectionSource(), cla) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除数据库的表结构
     */
    public boolean deleteTable(Class cla) {
        /**
         * 删除表
         * 1，连接源
         * 2，表信息
         * 3，是否忽略错误
         */
        try {
            return TableUtils.dropTable(getConnectionSource(), cla, true) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
