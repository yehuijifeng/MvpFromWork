package com.android.mvp.view.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.mvp.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Luhao on 2016/6/28.
 * 默认的listview
 */
public class BaseListView extends LinearLayout {
    private LayoutInflater inflater;//父容器
    public ListView listView;
    private PtrFrameLayout ptrFrameLayout;//下拉刷新控件
    private boolean isRefresh;//是否可以下拉刷新
    private boolean isLoadMore;//是否可以上拉加载
    private View root;//父布局
    private HeaderView headerView; //头布局
    public FootView footView;//尾布局
    private OnScollListener onScollListener;//列表的滑动监听

    public void setOnExecuteScoll(boolean isLoadMore) {
        onScollListener.setLoadSuccess(isLoadMore);
    }

    public void setRefresh(boolean refresh) {
        if (refresh) {
            ptrFrameLayout.addPtrUIHandler(headerView);
            headerView.setVisibility(VISIBLE);
        } else {
            headerView.setVisibility(GONE);
            ptrFrameLayout.getOffsetToRefresh();
            ptrFrameLayout.removePtrUIHandler(headerView);
        }
    }

    public void setLoadMore(boolean loadMore) {
        onScollListener.setLoadMore(loadMore);
        if (!loadMore)
            listView.removeFooterView(footView);
    }

    /**
     * 添加下拉刷新的监听
     */
    public void setRefreshListener(HeaderView.RefreshListener refreshListener) {
        headerView.setRefreshListener(refreshListener);
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

    public BaseListView(Context context) {
        super(context);
        initView();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflater = LayoutInflater.from(getContext());
        root = inflater.inflate(R.layout.base_listview, this);
        ptrFrameLayout = (PtrFrameLayout) root.findViewById(R.id.base_refresh_view);
        listView = (ListView) root.findViewById(R.id.base_list_view);
        headerView = new HeaderView(getContext());
        footView = new FootView(getContext());
        onScollListener = new OnScollListener(footView);
        listView.setOnScrollListener(onScollListener);
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
