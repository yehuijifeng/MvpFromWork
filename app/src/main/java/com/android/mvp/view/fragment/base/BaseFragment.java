package com.android.mvp.view.fragment.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.R;
import com.android.mvp.function.RxBus;
import com.android.mvp.http.StatusCode;
import com.android.mvp.http.request.RequestAction;
import com.android.mvp.http.response.ResponseAction;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.BasePresenter;
import com.android.mvp.utils.NetWorkUtils;
import com.android.mvp.view.activity.base.BaseHelper;
import com.android.mvp.view.baseview.LoadingView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by yehuijifeng
 * on 2015/12/6.
 * fragment的基本类
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    /**
     * 必须实例化presenter对象
     */
    protected abstract T initPresenter();

    /**
     * 布局id,传入了ToolBarActivity中重写的setContentView中，以便于添加toolbar
     */
    protected abstract int setFragmentViewContent();

    /**
     * 初始化view，传入父类view
     */
    protected abstract void initView(View parentView);

    /**
     * 初始化data
     */
    protected abstract void initData();

    /**
     * presenter对象负责具体的业务联系
     */
    protected T presenter;


    /**
     * activity和fragment的代理类
     */
    private BaseHelper helper;

    /**
     * 获取屏幕宽高
     */
    protected DisplayMetrics outMetrics = new DisplayMetrics();

    /**
     * gson,解析json数据或者类转json时能用到
     */
    protected Gson gson = new Gson();

    /**
     * imageloader工具类的初始化
     */
    protected ImageLoader imageLoader;

    /**
     * 父布局填充
     */
    protected ViewGroup root;

    /**
     * 该fragment是否处于显示状态
     */
    protected boolean isVisible;

    /**
     * 该fragment是否准备好了
     */
    protected boolean isPrepared;

    /**
     * 该fragment是否已经加载过了
     */
    protected boolean isLoaded;

    /**
     * loading页
     */
    protected LoadingView loadingView;

    /**
     * 创建视图,传入根view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(setFragmentViewContent(), container, false);
    }

    /**
     * 视图创建,当前视图被调用的时候，activity才会被传入进来
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initProperties(view);
        initView(view);
        isPrepared = true;//视图准备好了
        onVisible();
    }

    /**
     * 选中状态
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    //fragment 显示
    protected void onVisible() {
        if (!isVisible || !isPrepared) return;
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
        lazyLoad();
    }

    //fragment 隐藏
    protected void onInvisible() {
        if (isVisible || !isPrepared) return;
        if (presenter != null)
            presenter.onStop();
    }

    //延迟加载,每一个fragment的延迟加载只会调用一次
    protected void lazyLoad() {
        if (!isVisible || !isPrepared || isLoaded) return;
        initData();
        isLoaded = true;
    }

    private void initProperties(View parentView) {
        presenter = initPresenter();
        helper = new BaseFragmentHelper(getActivity(), this);
        root = (ViewGroup) parentView;
        imageLoader = ImageLoader.getInstance();
        outMetrics = helper.outMetrics;
        initLoadingView();
    }

    /**
     * loading遮罩层的加载
     */
    private void initLoadingView() {

    }

    protected void showLoading() {
        helper.showLoading();
    }

    protected void showLoading(String str) {
        helper.showLoading(str);
    }


    protected void showErrorLoading(String str, View.OnClickListener onClickListener) {
        helper.showErrorLoading(str, onClickListener);
    }

    protected void showErrorLoadingByNoClick(String str) {
        showErrorLoading(str, null);
    }

    protected void showErrorLoadingByDefaultClick(String str) {
        showErrorLoading(str, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    protected void showErrorBtnLoading(String str, String btnStr, View.OnClickListener onClickListener) {
        helper.showErrorBtnLoading(str, btnStr, onClickListener);
    }

    protected void showErrorBtnLoadingByDefaultClick(String str, String btnStr) {
        showErrorBtnLoading(str, btnStr, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    protected void closeLoading() {
        helper.closeLoading();
    }


    /**
     * 此方法以后才能获取activity
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 发送网络请求
     *
     * @param requesteAction
     */
    protected void sendRequest(RequestAction requesteAction) {
        if (!NetWorkUtils.isConnected(getActivity())) {
            //网络错误，服务器错误，等等
            ResponseAction responseAction = new ResponseFinalAction();
            responseAction.setRequestCode(StatusCode.NETWORK_ERROR);
            responseAction.setRequestAction(requesteAction);
            responseAction.setErrorMessage(getActivity().getResources().getString(R.string.network_error));
            RxBus.getDefault().post(responseAction);
        } else if (presenter != null) {
            presenter.sendRequest(requesteAction);
        }
    }

    /**
     * 提供给子类重写的刷新方法
     */
    protected void refresh() {

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

    protected void onRequestComplete(ResponseFinalAction complete) {

    }

    /**
     * 是否开启防抖动
     * Intent intent = new Intent();
     * intent.putExtra("user_address", addressInfoVo);
     * setResult(RESULT_OK, intent);
     * finish();
     */
    protected void setIsClick(boolean bl) {
        helper.setIsClick(bl);
    }

    protected int getWindowHeight() {
        return helper.getWindowHeight();
    }

    protected int getWindowWidth() {
        return helper.getWindowWidth();
    }


    protected Bundle getBundle() {
        return helper.getBundle();
    }

    protected int getInt(String key, int defaultValue) {
        return helper.getInt(key, defaultValue);
    }

    protected String getString(String key, String defaultValue) {
        return helper.getString(key, defaultValue);
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        return helper.getBoolean(key, defaultValue);
    }

    protected double getDouble(String key, double defaultValue) {
        return helper.getDouble(key, defaultValue);
    }

    protected float getFloat(String key, float defaultValue) {
        return helper.getFloat(key, defaultValue);
    }

    protected Parcelable getParcelable(String key) {
        return helper.getParcelable(key);
    }

    protected ArrayList<? extends Parcelable> getParcelableList(String key) {
        return helper.getParcelableList(key);
    }

    /**
     * 去设置网络
     */
    public void toSetNetWork() {
        Intent wifiSettingsIntent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(wifiSettingsIntent);
    }


    /**
     * 显式跳转Activity的方法(不带任何参数)
     *
     * @param cls 要跳转的Activity的类
     */
    public void startActivity(Class<?> cls) {
        helper.startActivity(cls);
    }

    /**
     * 显式跳转Activity的方法(带Bundle)
     *
     * @param cls    要跳转的Activity的类
     * @param bundle 装载了各种参数的Bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        helper.startActivity(cls, bundle);
    }

    /**
     * 显式跳转Activity的方法(带返回结果，不带参数)
     *
     * @param cls         要跳转的Activity的类
     * @param requestCode 跳转Activity的请求码
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        helper.startActivityForResult(cls, requestCode);
    }

    /**
     * 显式跳转Activity的方法(带返回结果，带Bundle)
     *
     * @param cls         要跳转的Activity的类
     * @param bundle      装载了各种参数的Bundle
     * @param requestCode 跳转Activity的请求码
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        helper.startActivityForResult(cls, bundle, requestCode);
    }

    /**
     * 弹出时间短暂的Toast
     *
     * @param text
     */
    public void showShortToast(String text) {
        if (helper != null)
            helper.showShortToast(text);
    }

    /**
     * 弹出时间较长的Toast
     *
     * @param text
     */
    public void showLongToast(String text) {
        if (helper != null)
            helper.showLongToast(text);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
