package com.android.mvp.view.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.utils.DateUtil;

import java.text.ParseException;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by yehuijifeng
 * on 2015/12/26.
 * 下拉刷新自定义的头
 */
public class HeaderView extends LinearLayout implements PtrUIHandler {

    private LayoutInflater inflater;

    // 下拉刷新视图（头部视图）
    private View headView;
    // 下拉刷新文字和时间
    private TextView custom_header_hint_text, custom_header_time;
    // 下拉图标
    private ImageView custom_header_image;
    //正在加载的状态
    private ProgressBar custom_header_bar;

    private String past_time, current_time;
    private RefreshListener refreshListener;

    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    /**
     * 下拉刷新监听接口
     */
    public interface RefreshListener {
        void onRefreshPrepare(boolean bl, PtrFrameLayout frame);

        void onRefreshBegin(boolean bl, PtrFrameLayout frame);

        void onRefreshComplete(boolean bl, PtrFrameLayout frame);
    }

    public HeaderView(Context context) {
        super(context);
        initView();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        inflater = LayoutInflater.from(getContext());//父容器
        past_time = getContext().getResources().getString(R.string.past_time);
        /**
         * 头部
         */
        headView = inflater.inflate(R.layout.base_headerview, this);
        custom_header_hint_text = (TextView) headView.findViewById(R.id.custom_header_hint_text);
        custom_header_time = (TextView) headView.findViewById(R.id.custom_header_time);
        custom_header_image = (ImageView) headView.findViewById(R.id.custom_header_image);
        custom_header_bar = (ProgressBar) headView.findViewById(R.id.custom_header_bar);
        custom_header_time.setText(DateUtil.getNow(DateUtil.getDatePattern()));
    }


    /**
     * 重置ui
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_normal));
        custom_header_bar.setVisibility(GONE);
        custom_header_image.setVisibility(VISIBLE);
        custom_header_image.setRotation(0);//图片旋转
    }

    /**
     * 准备刷新
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_normal));
        custom_header_bar.setVisibility(GONE);
        custom_header_image.setVisibility(VISIBLE);
        custom_header_image.setRotation(0);//图片旋转
        getRefreshListener().onRefreshPrepare(true, frame);
        try {
            if (current_time != null)
                custom_header_time.setText(DateUtil.getTimeReduction(current_time));
            else
                custom_header_time.setText(DateUtil.getNow(DateUtil.getDatePattern()));
        } catch (ParseException e) {
            e.printStackTrace();
            custom_header_time.setText(DateUtil.getNow(DateUtil.getDatePattern()));
        }
    }

    /**
     * 开始刷新
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_normal_ing));
        custom_header_bar.setVisibility(VISIBLE);
        custom_header_image.setVisibility(GONE);
        getRefreshListener().onRefreshBegin(true, frame);
    }

    /**
     * 完成刷新
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_normal_over));
        custom_header_bar.setVisibility(GONE);
        custom_header_image.setVisibility(VISIBLE);
        getRefreshListener().onRefreshComplete(true, frame);
        current_time = DateUtil.getNow(DateUtil.getDatePattern());
    }

//    @Override
//    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, int oldPosition, int currentPosition, float oldPercent, float currentPercent) {
//        int mOffsetToRefresh = frame.getOffsetToRefresh();
//        /**如果视图的达到下拉刷新高度大于当前位置，并且小于或等于原来的视图的高度则为下拉刷新未达到状态*/
//        if (currentPosition > mOffsetToRefresh && oldPosition <= mOffsetToRefresh) {
//            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_ready));
//                custom_header_bar.setVisibility(GONE);
//                custom_header_image.setVisibility(VISIBLE);
//                custom_header_image.setRotation(180);//图片旋转
//            }
//        } else if (currentPosition < mOffsetToRefresh && oldPosition > mOffsetToRefresh) {
//            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_normal_over));
//                custom_header_bar.setVisibility(GONE);
//                custom_header_image.setVisibility(VISIBLE);
//                custom_header_image.setRotation(0);//图片旋转
//            }
//        }
//    }

    /**
     * 位置改变
     *
     * @param frame
     * @param isUnderTouch 是否在触摸
     * @param status       状态码：
     *                     PTR_STATUS_INIT = 1; 初始化
     *                     PTR_STATUS_PREPARE = 2; 准备
     *                     PTR_STATUS_LOADING = 3; 加载中
     *                     PTR_STATUS_COMPLETE = 4; 加载完成
     * @param ptrIndicator 指示器
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int mOffsetToRefresh = frame.getOffsetToRefresh();
        /**如果视图的达到下拉刷新高度大于当前位置，并且小于或等于原来的视图的高度则为下拉刷新未达到状态*/

        if (ptrIndicator.getCurrentPosY() > mOffsetToRefresh && ptrIndicator.getLastPosY() <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_ready));
                custom_header_bar.setVisibility(GONE);
                custom_header_image.setVisibility(VISIBLE);
                custom_header_image.setRotation(180);//图片旋转
            }
        } else if (ptrIndicator.getCurrentPosY() < mOffsetToRefresh && ptrIndicator.getLastPosY() > mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                custom_header_hint_text.setText(getContext().getResources().getString(R.string.header_hint_normal_over));
                custom_header_bar.setVisibility(GONE);
                custom_header_image.setVisibility(VISIBLE);
                custom_header_image.setRotation(0);//图片旋转
            }
        }
    }
}
