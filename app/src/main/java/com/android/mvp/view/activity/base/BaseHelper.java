package com.android.mvp.view.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.mvp.R;
import com.android.mvp.appliaction.ActivityCollector;
import com.android.mvp.function.RxBus;
import com.android.mvp.http.StatusCode;
import com.android.mvp.http.request.RequestAction;
import com.android.mvp.http.response.ResponseAction;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩 on 2016/6/23.
 * 所有基类activity和fragment的功能代理类
 */
public class BaseHelper {
    private BaseActivity activity;
    private Class activityClass;//暂存需要跳转的activity，防抖动
    public DisplayMetrics outMetrics = new DisplayMetrics();//测量当前屏幕
    /**
     * 父布局
     */
    public LayoutInflater inflater;

    /**
     * toast弹出信息
     */
    public Toast toast;

    public BaseHelper(Context context) {
        this.activity = (BaseActivity) context;
        activityClass = activity.getClass();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        inflater = activity.getLayoutInflater();
    }

    /**
     * 发送网络请求
     *
     * @param requesteAction
     */
    protected void sendRequest(RequestAction requesteAction) {
        if (!NetWorkUtils.isConnected(activity)) {
            //网络错误，服务器错误，等等
            ResponseAction responseAction = new ResponseFinalAction();
            responseAction.setRequestCode(StatusCode.NETWORK_ERROR);
            responseAction.setRequestAction(requesteAction);
            responseAction.setErrorMessage(activity.getResources().getString(R.string.network_error));
            RxBus.getDefault().post(responseAction);
        } else if (activity.presenter != null) {
            activity.presenter.sendRequest(requesteAction);
        }
    }

    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        if (isFastClick(cls)) return;
        else activityClass = cls;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 显式跳转Activity的方法(带返回结果，不带参数)
     *
     * @param cls         要跳转的Activity的类
     * @param requestCode 跳转Activity的请求码
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 显式跳转Activity的方法(带返回结果，带Bundle)
     *
     * @param cls         要跳转的Activity的类
     * @param bundle      装载了各种参数的Bundle
     * @param requestCode 跳转Activity的请求码
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        if (isFastClick(cls)) return;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @return 返回当前屏幕的高
     */
    public int getWindowHeight() {
        return outMetrics.heightPixels;
    }

    /**
     * @return 返回当前屏幕的宽
     */
    public int getWindowWidth() {
        return outMetrics.widthPixels;
    }


    /**
     * @return 返回当前intent中的bundle
     */
    public Bundle getBundle() {
        Intent intent = activity.getIntent();
        return intent == null ? null : intent.getExtras();
    }

    /**
     * @param key          对应bundle中的标识
     * @param defaultValue 默认值，娶不到bundle中的值的时候返回该默认值
     */
    public int getInt(String key, int defaultValue) {
        if (getBundle() != null)
            return getBundle().getInt(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * 下同，，，，bundle的用法
     */
    public String getString(String key, String defaultValue) {
        if (getBundle() != null)
            return getBundle().getString(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * 获取上一个Activity传过来的布尔值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return getBundle() != null && getBundle().getBoolean(key, defaultValue);
    }

    /**
     * 获取上一个Activity传过来的double值
     */
    public double getDouble(String key, double defaultValue) {
        if (getBundle() != null)
            return getBundle().getDouble(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * 获取上一个Activity传过来的float值
     */
    public float getFloat(String key, float defaultValue) {
        if (getBundle() != null)
            return getBundle().getFloat(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * 获取上一个Activity传过来的实现了Parcelable接口的对象
     */
    public Parcelable getParcelable(String key) {
        if (getBundle() != null)
            return getBundle().getParcelable(key);
        else
            return null;
    }

    /**
     * 获取上一个Activity传过来的字符串集合
     */
    public List<String> getStringArrayList(String key) {
        if (getBundle() != null)
            return getBundle().getStringArrayList(key);
        else
            return null;
    }

    /**
     * 获取上一个Activity传过来的实现了Parcelable接口的对象的集合
     */
    public ArrayList<? extends Parcelable> getParcelableList(String key) {
        if (getBundle() != null)
            return getBundle().getParcelableArrayList(key);
        else
            return null;
    }

    /**
     * 弹出时间短暂的Toast
     */
    public void showShortToast(String text) {
        if (toast == null)
            toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
        else
            toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(text);

        toast.show();
    }

    /**
     * 弹出时间较长的Toast
     */
    public void showLongToast(String text) {
        if (toast == null)
            toast = Toast.makeText(activity, text, Toast.LENGTH_LONG);
        else
            toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(text);
        toast.show();
    }

    /**
     * 获得当前activity的实例
     */
    public BaseActivity getActivity() {
        return activity;
    }

    /**
     * 释放activity
     * 如果该activity被内存泄漏则需要手动释放该资源
     */
    protected void releaseActivity() {
        activity = null;
    }

    /**
     * 退出程序
     */
    protected void finishAll() {
        ActivityCollector.finishAll();
    }


    /**
     * 防抖点击事件
     *
     * @param cla
     * @return
     */
    protected boolean isFastClick(Class cla) {
        if (!isClick) return false;
        if (isFastClick(500)) {
            if (activityClass == cla) {
                return true;
            } else {
                activityClass = cla;
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isClick = true;

    //设置是否开启防抖动
    public void setIsClick(boolean bl) {
        isClick = bl;
    }

    private long lastTime;

    private boolean isFastClick(int limit) {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastTime;
        lastTime = currentTime;
        return timeDiff < limit;
    }
}
