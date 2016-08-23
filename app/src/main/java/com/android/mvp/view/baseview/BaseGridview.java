package com.android.mvp.view.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.mvp.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Luhao on 2016/8/22.
 */
public class BaseGridview extends LinearLayout {

    private LayoutInflater inflater;//父容器
    public MyGridView myGridView;
    private PtrFrameLayout ptrFrameLayout;//下拉刷新控件
    private boolean isRefresh;//是否可以下拉刷新
    private boolean isLoadMore;//是否可以上拉加载
    private View root;//父布局
    private HeaderView headerView; //头布局
    public FootView footView;//尾布局
    private OnScollListener onScollListener;//列表的滑动监听

    /**
     * 加载成功，初始化上拉加载，外界传入true，则初始化上拉加载
     *
     * @param isLoadMore
     */
    public void setOnExecuteScoll(boolean isLoadMore) {
        onScollListener.setLoadSuccess(isLoadMore);
    }

    /**
     * 设置下拉刷新控件是否可以刷新
     *
     * @param refresh
     */
    public void setRefresh(boolean refresh) {
        if (ptrFrameLayout != null)
            ptrFrameLayout.setEnabled(refresh);
    }

    /**
     * 设置加载更多
     *
     * @param loadMore
     */
    public void setLoadMore(boolean loadMore) {
        onScollListener.setLoadMore(loadMore);
        //if (!loadMore)
        //myGridView.removeFooterView(footView);
    }

    /**
     * 添加上拉加载的监听
     */
    public void setLoadMoreListener(FootView.LoadMoreListener loadMoreListener) {
        footView.setLoadMoreListener(loadMoreListener);
    }

    /**
     * 添加列表滑动状态的监听
     */
    public void setSidingStatusListener(OnScollListener.SidingStatusListener sidingStatusListener) {
        onScollListener.setSidingStatusListener(sidingStatusListener);
    }


    public BaseGridview(Context context) {
        super(context);
        initView();
    }

    public BaseGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflater = LayoutInflater.from(getContext());
        root = inflater.inflate(R.layout.base_gridview, this);
        ptrFrameLayout = (PtrFrameLayout) root.findViewById(R.id.base_refresh_view);
        myGridView = (MyGridView) root.findViewById(R.id.my_gridview);
        headerView = new HeaderView(getContext());
        footView = new FootView(getContext());
        onScollListener = new OnScollListener(footView);
        myGridView.setOnScrollListener(onScollListener);
        ptrFrameLayout.setHeaderView(headerView);
        ptrFrameLayout.addPtrUIHandler(headerView);
        /**
         * 注意！
         * 该回调方法必须写！
         * 如果不写则下拉刷新不正常！
         * ptrFrameLayout.refreshComplete();为刷新完成还原视图的方法可以在实现了该回调后自行处理
         */
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                //ptrFrameLayout.refreshComplete();//刷新完成
                //ptrFrameLayout.autoRefresh();//自动刷新
            }
        });
        ptrFrameLayout.setPullToRefresh(false);
    }

    /**
     * 刷新完成成功后的回调
     */
    public void closeRefreshView() {
        if (ptrFrameLayout != null) {
            ptrFrameLayout.refreshComplete();
        }
    }
}
