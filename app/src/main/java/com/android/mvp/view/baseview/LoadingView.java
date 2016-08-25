package com.android.mvp.view.baseview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mvp.R;


/**
 * Created by 浩 on 2016/6/28.
 * loading页的视图
 */
public class LoadingView extends LinearLayout implements View.OnTouchListener {
    private View root;//根view
    private LinearLayout loading_ly, loading_click_ly;//根布局
    private LinearLayout dialog_ly;//dialog样式专用布局
    private LinearLayout loading_son_ly;//调整loading的方向
    private ImageView error_icon_img;//错误提示图片
    private TextView error_str_text;//错误提示文字
    private Button error_btn;//错误提示后的点击事件
    private TextView loading_str_text;//加载提示文字
    private ImageView loading_icon_img;//加载旋转的图片
    private Animation animation;//旋转动画
    private LinearInterpolator linearInterpolator;//线性插值器，根据时间百分比设置属性百分比

    public LoadingView(Context context) {
        super(context);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        if (root != null) root.setVisibility(GONE);
        root = LayoutInflater.from(getContext()).inflate(R.layout.base_loadingview, this);
        loading_ly = (LinearLayout) root.findViewById(R.id.loading_ly);
        loading_click_ly = (LinearLayout) root.findViewById(R.id.loading_click_ly);
        dialog_ly = (LinearLayout) root.findViewById(R.id.dialog_ly);
        loading_son_ly = (LinearLayout) root.findViewById(R.id.loading_son_ly);
        loading_ly.setOnTouchListener(this);
        error_icon_img = (ImageView) root.findViewById(R.id.error_icon_img);
        error_str_text = (TextView) root.findViewById(R.id.error_str_text);
        error_btn = (Button) root.findViewById(R.id.error_btn);
        loading_str_text = (TextView) root.findViewById(R.id.loading_str_text);
        loading_icon_img = (ImageView) root.findViewById(R.id.loading_icon_img);
        root.setVisibility(View.GONE);
        //设置旋转动画
        animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//子心旋转
        linearInterpolator = new LinearInterpolator();
        //setInterpolator表示设置旋转速率。
        animation.setInterpolator(linearInterpolator);
        animation.setRepeatCount(-1);//-1表示循环运行
        animation.setDuration(1000);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.loading_ly) {
            //如果用户点击了loading层，则点击事件不往下传递

            return true;
        }
        return false;
    }

    private void initRootView() {
        if (root == null)
            initView();
        root.setVisibility(VISIBLE);
        loading_ly.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        dialog_ly.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        loading_son_ly.setOrientation(HORIZONTAL);
    }

    /**
     * 设置loadingview的背景色
     * 默认半透明
     *
     * @param color R.color.while
     */
    public void setLoadingViewBackground(int color) {
        loading_ly.setBackgroundColor(getContext().getResources().getColor(color));
        dialog_ly.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
    }


    /**
     * dialog样式的loading页
     */
    public void getDialogStyle() {
        loading_ly.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        dialog_ly.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_base_loading_dialog));
        loading_son_ly.setOrientation(VERTICAL);//将试图垂直
    }

    /**
     * 遮罩全屏的loading页
     */
    public void getFullWindowStyle() {
        loading_ly.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        dialog_ly.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        loading_son_ly.setOrientation(HORIZONTAL);
    }

    /**
     * @param drawable   loadingye加载的旋转图片
     * @param loadingStr loading加载提示文字
     */
    public void showLoading(Drawable drawable, String loadingStr) {
        if (root.getVisibility() == View.VISIBLE) return;
        initRootView();
        getFullWindowStyle();
        error_icon_img.setVisibility(GONE);
        error_str_text.setVisibility(GONE);
        error_btn.setVisibility(GONE);
        loading_icon_img.setVisibility(VISIBLE);
        loading_str_text.setVisibility(VISIBLE);
        if (drawable != null)
            loading_icon_img.setImageDrawable(drawable);
        if (!TextUtils.isEmpty(loadingStr))
            loading_str_text.setText(loadingStr);
        loading_icon_img.startAnimation(animation);
    }

    public void showLoading(String loadingStr) {
        showLoading(null, loadingStr);
    }

    /**
     * 关闭loading页
     */
    public void closeLoadingView() {
        loading_icon_img.clearAnimation();
        root.setVisibility(GONE);
    }

    /**
     * 错误按钮点击事件
     *
     * @param onClickListener
     */
    public void setErrorBtnClickListener(OnClickListener onClickListener) {
        initRootView();
        if (error_btn != null) error_btn.setOnClickListener(onClickListener);
    }

    public void setErrorBtnClickListener(String str, OnClickListener onClickListener) {
        if (error_btn != null && !TextUtils.isEmpty(str)) error_btn.setText(str);
        setErrorBtnClickListener(onClickListener);
    }

    public void setErrorClickListener(OnClickListener onClickListener) {
        if (loading_click_ly != null)
            loading_click_ly.setOnClickListener(onClickListener);
    }

    /**
     * 显示错误提示
     *
     * @param drawable
     * @param errorStr
     */
    public void showErrorPrompt(Drawable drawable, String errorStr) {
        if (root.getVisibility() == View.VISIBLE) return;
        initRootView();
        getFullWindowStyle();
        loading_icon_img.setVisibility(GONE);
        loading_str_text.setVisibility(GONE);
        error_btn.setVisibility(GONE);
        error_icon_img.setVisibility(VISIBLE);
        error_str_text.setVisibility(VISIBLE);
        if (drawable != null)
            error_icon_img.setImageDrawable(drawable);
        if (!TextUtils.isEmpty(errorStr))
            error_str_text.setText(errorStr);
    }

    public void showErrorPrompt(String errorStr) {
        showErrorPrompt(null, errorStr);
    }

    /**
     * 显示带按钮的错误提示
     *
     * @param btnDrawable
     * @param errorStr
     */
    public void showErrorBtnPrompt(Drawable btnDrawable, String errorStr) {
        if (root.getVisibility() == View.VISIBLE) return;
        initRootView();
        getFullWindowStyle();
        loading_icon_img.setVisibility(GONE);
        loading_str_text.setVisibility(GONE);
        error_icon_img.setVisibility(GONE);
        error_btn.setVisibility(VISIBLE);
        error_str_text.setVisibility(VISIBLE);
        if (btnDrawable != null)
            error_btn.setBackgroundDrawable(btnDrawable);
        if (!TextUtils.isEmpty(errorStr))
            error_str_text.setText(errorStr);
    }

    public void showErrorBtnPrompt(String errorStr) {
        showErrorBtnPrompt(null, errorStr);
    }
}
