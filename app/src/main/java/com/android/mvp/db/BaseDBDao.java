package com.android.mvp.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Luhao
 * on 2016/1/6.
 * 数据库dao方法的代理类
 */
public class BaseDBDao {

    /**
     * 条件查询QueryBuilder的使用：
     * 1、简单的where 条件
     * articleDaoOpe.queryBuilder().where().eq("user_id", userId).query();直接返回Article的列表
     * <p>
     * 2,where and
     * QueryBuilder<Article, Integer> queryBuilder = articleDaoOpe
     * .queryBuilder();
     * Where<Article, Integer> where = queryBuilder.where();
     * where.eq("user_id", 1);
     * where.and();
     * where.eq("name", "xxx");
     * <p>
     * //或者
     * articleDaoOpe.queryBuilder().//
     * where().//
     * eq("user_id", 1).and().//
     * eq("name", "xxx");
     * <p>
     * <p>
     * 上述两种都相当与：select * from tb_article where user_id = 1 and name = 'xxx' ;
     * <p>
     * <p>
     * 3、更复杂的查询
     * <p>
     * where.or(
     * where.and(
     * where.eq("user_id", 1),
     * where.eq("name", "xxx")),
     * where.and(where.eq("user_id", 2),
     * where.eq("name", "yyy")));
     * <p>
     * 相当于：select * from tb_article where ( user_id = 1 and name = 'xxx' )  or ( user_id = 2 and name = 'yyy' )  ;
     * <p>
     * <p>
     * 4，事务操作
     * TransactionManager.callInTransaction(helper.getConnectionSource(),
     * new Callable<Void>()
     * {
     *
     * @Override public Void call() throws Exception
     * {
     * return null;
     * }
     * });
     */

    /**
     * 数据库帮助类
     */
    protected BaseDBHelper dBHelper;
    public Dao<Object, Integer> dao;
    protected Class cla;

    /**
     * 工具类中操作增删改查的dao方法
     * 注意：初始化dao类的时候，数据库表已经被创建
     */
    public BaseDBDao(Context context, Class cla) {
        if (dBHelper == null)
            dBHelper = BaseDBHelper.getInstance(context);//获得数据库帮助类并创建数据库
        this.cla = cla;
        try {
            dao = dBHelper.getDao(cla);//获得dao实例，根据传入的实体类来
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 是否有数据库辅助类对象存在
     */
    public boolean isOpenDB() {
        return dao != null;
    }

    /**
     * 获得userDao
     *
     * @return
     * @throws SQLException
     */
    public synchronized Dao getDao() {
        if (!isOpenDB()) return null;
        return dao;
    }

    //删除表
    public boolean deleteTable() {
        if (!isOpenDB()) return false;
        return dBHelper.deleteTable(cla);
    }

    /**
     * 修改表结构
     */
    public boolean updateTable() {
        return dBHelper.updateTable(cla);
    }

    //重置表，清空数据
    public boolean resetTable() {
        return dBHelper.updateTable(cla);
    }

    //添加数据
    public int insertData(Object obj) {
        try {
            if (!isOpenDB()) return 0;
            return dao.create(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //修改数据
    public int updateData(Object obj) {
        try {
            if (!isOpenDB()) return 0;
            return dao.update(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //删除数据，根据id来
    public int deleteDataById(int id) {
        try {
            if (!isOpenDB()) return 0;
            return dao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //删除数据，根据实体类来
    public int deleteData(Object obj) {
        try {
            if (!isOpenDB()) return 0;
            return dao.delete(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Object> queryAll() {
        try {
            if (!isOpenDB()) return null;
            return dao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过UserId获取所有的文章
     *
     * @param userId
     * @return
     */
    public List<Object> queryByUserId(String userIdStr, int userId) {
        try {
            return dao.queryBuilder().where().eq(userIdStr, userId).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> queryByUserName(String userNameStr, String userName) {
        try {
            return dao.queryBuilder().where().eq(userNameStr, userName).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放资源
     */
    public void closeDao() {
        dao = null;
        dBHelper = null;
    }

}
