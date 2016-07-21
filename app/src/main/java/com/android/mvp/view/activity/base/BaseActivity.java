package com.android.mvp.view.activity.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.mvp.R;
import com.android.mvp.appliaction.ActivityCollector;
import com.android.mvp.http.response.ResponseAction;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.BasePresenter;
import com.android.mvp.view.baseview.LoadingView;
import com.android.mvp.view.baseview.MyTitleView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Luhao on 2016/6/21.
 * 所有activity的基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    //必须实例化presenter对象
    protected abstract T initPresenter();

    //必须传入activity的view
    protected abstract int setContentView();

    //初始化view
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //初始化title
    protected abstract String setTitleText();

    protected T presenter;

    private BaseHelper baseHelper;
    /**
     * imageloader工具类的初始化
     */
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    /**
     * 父布局填充
     */
    public LayoutInflater inflater;
    /**
     * 根布局
     */
    protected ViewGroup root, rootGroup;

    /**
     * loading页
     */
    protected LoadingView loadingView;

    /**
     * activity的title
     */
    protected MyTitleView mTitleView;
    /**
     * title的类型，枚举类型,初始化给默认标题类型
     */
    protected MyTitleView.TitleMode titleMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横竖屏
        setContentView(setContentView());
        //沉浸式title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initialize();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
            presenter.subscription = presenter.observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ResponseAction>() {
                        @Override
                        public void call(ResponseAction responseAction) {
                            if (responseAction instanceof ResponseSuccessAction) {
                                onRequestSuccess((ResponseSuccessAction) responseAction);
                            } else if (responseAction instanceof ResponseFinalAction) {
                                onRequestFinal((ResponseFinalAction) responseAction);
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    /**
     * 去设置网络
     */
    public void toSetNetWork() {
        Intent wifiSettingsIntent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(wifiSettingsIntent);
    }

    /**
     * 在baseactivity内部的初始化工作
     */
    private void initialize() {
        presenter = initPresenter();
        ActivityCollector.addActivity(this);
        baseHelper = new BaseHelper(this);
        rootGroup = (ViewGroup) findViewById(android.R.id.content);
        root = (ViewGroup) rootGroup.getChildAt(0);
        inflater = this.getLayoutInflater();
        mTitleView = (MyTitleView) findViewById(R.id.default_title_view);
        if (mTitleView != null) {
            if (setCustomToolbar() != null) {
                onCreateCustomToolBar(setCustomToolbar());
            } else {
                mTitleView.setTitleText(setTitleText());
            }
        }
        initLoadingView();
    }

    /**
     * loading遮罩层的加载
     */
    private void initLoadingView() {
        loadingView = new LoadingView(this);
        rootGroup.addView(loadingView);
    }

    protected void showLoading() {
        loadingView.showLoading("正在加载中……");
    }

    protected void showLoading(String str) {
        loadingView.showLoading(str);
    }

    protected void showError(String str) {
        loadingView.showErrorPrompt(str);
    }

    protected void closeLoading() {
        loadingView.closeLoadingView();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    protected int getWindowHeight() {
        return baseHelper.getWindowHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    protected int getWindowWidth() {
        return baseHelper.getWindowWidth();
    }

    /**
     * 供子类调用的title,自定义toolbar
     */
    protected View setCustomToolbar() {
        return null;
    }

    /**
     * @param view 创建自定义的toolbar
     */
    protected void onCreateCustomToolBar(View view) {
        mTitleView.setNewView(view);
    }


    /**
     * 跳转activity
     *
     * @param cla
     */
    protected void startActivity(Class cla) {
        startActivity(cla, null);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        baseHelper.startActivity(cls, bundle);
    }

    /**
     * 显式跳转Activity的方法(带返回结果，不带参数)
     *
     * @param cls         要跳转的Activity的类
     * @param requestCode 跳转Activity的请求码
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        baseHelper.startActivityForResult(cls, requestCode);
    }

    /**
     * 显式跳转Activity的方法(带返回结果，带Bundle)
     *
     * @param cls         要跳转的Activity的类
     * @param bundle      装载了各种参数的Bundle
     * @param requestCode 跳转Activity的请求码
     */
    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        baseHelper.startActivityForResult(cls, bundle, requestCode);
    }


    /**
     * 获取上一个Activity传过来的Bundle
     */
    protected Bundle getBundle() {
        return baseHelper.getBundle();
    }

    /**
     * 获取上一个Activity传过来的字符串集合
     */
    protected List<String> getStringArrayList(String key) {
        return baseHelper.getStringArrayList(key);
    }

    /**
     * 获取上一个Activity传过来的int值
     *
     * @param key          键
     * @param defaultValue 默认值
     */
    protected int getInt(String key, int defaultValue) {
        return baseHelper.getInt(key, defaultValue);
    }

    /**
     * 获取上一个Activity传过来的String值
     */
    protected String getString(String key, String defaultValue) {
        return baseHelper.getString(key, defaultValue);
    }

    /**
     * 获取上一个Activity传过来的布尔值
     */
    protected boolean getBoolean(String key, boolean defaultValue) {
        return baseHelper.getBoolean(key, defaultValue);
    }

    /**
     * 获取上一个Activity传过来的double值
     */
    protected double getDouble(String key, double defaultValue) {
        return baseHelper.getDouble(key, defaultValue);
    }

    /**
     * 获取上一个Activity传过来的float值
     */
    protected float getFloat(String key, float defaultValue) {
        return baseHelper.getFloat(key, defaultValue);
    }

    /**
     * 获取上一个Activity传过来的实现了Parcelable接口的对象
     */
    protected Parcelable getParcelable(String key) {
        return baseHelper.getParcelable(key);
    }

    /**
     * 获取上一个Activity传过来的实现了Parcelable接口的对象的集合
     */
    protected ArrayList<? extends Parcelable> getParcelableList(String key) {
        return baseHelper.getParcelableList(key);
    }


    /**
     * Intent intent = new Intent();
     * intent.putExtra("user_address", addressInfoVo);
     * setResult(RESULT_OK, intent);
     * finish();
     */

    protected void setIsClick(boolean bl) {
        baseHelper.setIsClick(bl);
    }

    /**
     * 提供给子类继承
     * 网路请求返回结果
     *
     * @param success
     */
    protected void onRequestSuccess(ResponseSuccessAction success) {
    }

    protected void onRequestFinal(ResponseFinalAction finals) {

    }

    /**
     * 弹出时间短暂的Toast
     */
    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出时间较长的Toast
     */
    protected void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        baseHelper.releaseActivity();
        baseHelper = null;
        if (presenter != null) {
            presenter.onDestory();
        }
    }
}
