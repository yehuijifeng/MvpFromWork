package com.android.mvp.view.baseview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mvp.R;


/**
 * Created by yehuijifeng on 2015/11/29.
 * 默认app标题
 */
public class MyTitleView extends LinearLayout {

    private ImageView title_default_back, title_default_right_image, toolbar_color_img;
    private TextView title_default_text, title_default_right_text;
    private LinearLayout toolbar_ly;
    private LinearLayout toolbar_color_rl;
    private View root;
    private LoadingView loadingView;

    public LoadingView getLoadingView() {
        return loadingView;
    }

    //默认标题类型
    private TitleMode mTitleMode = TitleMode.NORMAL;

    public MyTitleView(Context context) {
        super(context);
        initView(context);
    }

    public MyTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.base_titleview, this);
        toolbar_ly = (LinearLayout) root.findViewById(R.id.toolbar_ly);
        title_default_right_image = (ImageView) root.findViewById(R.id.title_default_right_image);
        toolbar_color_rl = (LinearLayout) root.findViewById(R.id.toolbar_color_rl);
        title_default_back = (ImageView) root.findViewById(R.id.title_default_back);
        toolbar_color_img = (ImageView) root.findViewById(R.id.toolbar_color_img);
        title_default_text = (TextView) root.findViewById(R.id.title_default_text);
        title_default_right_text = (TextView) root.findViewById(R.id.title_default_right_text);
        title_default_back.setOnClickListener(toBack);
        setTitleMode(TitleMode.NORMAL);
        loadingView = (LoadingView) root.findViewById(R.id.default_loading_view);

    }

    /**
     * 将title空出一个标题栏的高度
     *
     * @param barHeight
     */
    public void setTitleBarHeight(final int barHeight) {
        ViewGroup.LayoutParams layoutParams = toolbar_color_img.getLayoutParams();
        layoutParams.height = barHeight;
        toolbar_color_img.setLayoutParams(layoutParams);
    }

    public void setTitleColor(int color) {
        toolbar_color_rl.setBackgroundColor(color);
    }

    /**
     * 创建自定义title
     */
    public void setNewView(View view) {
        if (toolbar_ly == null)
            toolbar_ly = (LinearLayout) findViewById(R.id.toolbar_ly);
        toolbar_ly.removeAllViews();
        toolbar_ly.addView(view);
    }

    //没有返回键的title
    private void initNoBackNormalTitle() {
        title_default_text.setVisibility(View.VISIBLE);
        title_default_right_text.setVisibility(View.GONE);
        title_default_back.setVisibility(View.GONE);
        title_default_right_image.setVisibility(View.GONE);
    }

    //正常的title
    private void initNormalTitle() {
        title_default_text.setVisibility(View.VISIBLE);
        title_default_right_text.setVisibility(View.GONE);
        title_default_back.setVisibility(View.VISIBLE);
        title_default_right_image.setVisibility(View.INVISIBLE);
    }

    //带有编辑按钮的title
    private void initEditableTitle() {
        title_default_text.setVisibility(View.VISIBLE);
        title_default_right_text.setVisibility(View.VISIBLE);
        title_default_back.setVisibility(View.VISIBLE);
        title_default_right_image.setVisibility(View.GONE);
    }

    //没有返回键带编辑按钮的title
    private void initNoBackEditableTitle() {
        title_default_text.setVisibility(View.VISIBLE);
        title_default_right_text.setVisibility(View.VISIBLE);
        title_default_back.setVisibility(View.INVISIBLE);
        title_default_right_image.setVisibility(View.GONE);
    }

    //带图片按钮的正常title
    private void initOptionsTitle() {
        title_default_text.setVisibility(View.VISIBLE);
        title_default_right_text.setVisibility(View.GONE);
        title_default_back.setVisibility(View.VISIBLE);
        title_default_right_image.setVisibility(View.VISIBLE);
    }

    //无返回键无图片按钮的title
    private void initNoBackOptionsTitle() {
        title_default_text.setVisibility(View.VISIBLE);
        title_default_right_text.setVisibility(View.GONE);
        title_default_back.setVisibility(View.INVISIBLE);
        title_default_right_image.setVisibility(View.VISIBLE);
    }


    /**
     * 当传入layout的时候监听
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        switch (getTitleMode()) {
            case NORMAL://正常的title
                initNormalTitle();
                break;
            case EDITABLE://带有编辑按钮的title
                initEditableTitle();
                break;
            case OPTIONS://带有图片按钮的title
                initOptionsTitle();
                break;
            case NO_BACK_NORMAL://无返回键的正常title
                initNoBackNormalTitle();
                break;
            case NO_BACK_TEXT://无返回键带编辑按钮的title
                initNoBackEditableTitle();
                break;
            case NO_BACK_IMAGE://无返回键无图片按钮的title
                initNoBackOptionsTitle();
                break;
        }
    }

    //返回键点击事件
    private OnClickListener toBack = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) getContext()).finish();
        }
    };

    //返回按钮点击事件
    public void setToBackDrawable(Drawable drawable) {
        title_default_back.setImageDrawable(drawable);
    }

    //返回按钮点击事件
    public void setToBackOnClick(OnClickListener l) {
        title_default_back.setOnClickListener(l);
    }

    //标题内容
    public void setTitleText(String titleStr) {
        title_default_text.setText(titleStr);
    }


    //图片按钮点击事件
    public void setImageButtonOnClick(OnClickListener l) {
        title_default_right_image.setOnClickListener(l);
    }

    //图片按钮的图片资源
    public void setImageButtonDrawable(Drawable drawable) {
        title_default_right_image.setImageDrawable(drawable);
    }

    //编辑按钮文本内容
    public void setEditButtonContent(String content) {
        title_default_right_text.setText(content);
    }

    //编辑按钮文本大小
    public void setEditButtonSize(int size) {
        title_default_right_text.setTextSize(size);
    }

    //编辑按钮字体颜色
    public void setEditButtonColor(int color) {
        title_default_right_text.setTextColor(color);
    }

    //编辑按钮点击事件
    public void setEditButtonOnClick(OnClickListener l) {
        title_default_right_text.setOnClickListener(l);
    }

    //获取当前默认的title
    public TitleMode getTitleMode() {
        return this.mTitleMode;
    }

    //标题字体颜色
    public void setTitleTextColor(int color) {
        title_default_text.setTextColor(color);
    }

    //标题字体大小
    public void setTitleTextSize(int size) {
        title_default_text.setTextSize(size);
    }


    public void setTitleMode(TitleMode titleMode) {
        this.mTitleMode = titleMode;
    }

    public enum TitleMode {
        /**
         * 普通型标题(只有返回按钮和标题)
         */
        NORMAL,
        /**
         * 带编辑按钮的标题
         */
        EDITABLE,
        /**
         * 带设置图标，或其他图标的标题
         */
        OPTIONS,
        /**
         * 不带返回按钮的普通标题
         */
        NO_BACK_NORMAL,

        /**
         * 不带返回按钮但是带编辑按钮的标题
         */
        NO_BACK_TEXT,

        /**
         * 不带返回按钮但是带设置按钮的标题
         */
        NO_BACK_IMAGE
    }

}
