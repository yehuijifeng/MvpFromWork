package com.android.mvp.view.baseview;

import android.widget.AbsListView;


/**
 * Created by Luhao on 2016/6/28.
 * listview和gridview的监听
 */
public class OnScollListener implements AbsListView.OnScrollListener {
    private FootView footView;
    protected SidingStatusListener sidingStatusListener;
    protected boolean isLoadMore = true, isLoadStatus, isLoadComplete;
    private boolean isLoadSuccess = true;//该状态用于控制重复的加载，当第一次加载更多没有完成的时候，该状态一直是false

    public boolean isLoadSuccess() {
        return isLoadSuccess;
    }

    public void setLoadSuccess(boolean loadSuccess) {
        isLoadSuccess = loadSuccess;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public OnScollListener(FootView footView) {
        this.footView = footView;
    }

    public void setSidingStatusListener(SidingStatusListener sidingStatusListener) {
        this.sidingStatusListener = sidingStatusListener;
    }

    public interface SidingStatusListener {
        void onScrollStateChanged(AbsListView view, int scrollState);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (sidingStatusListener != null)
            sidingStatusListener.onScrollStateChanged(view, scrollState);
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {//滚动停止的时候
            //LogUtils.i("滑动后手指抬起");
            if (isLoadStatus) {
                //LogUtils.i("滑到最后了");
                if (isLoadSuccess()) {
                   // LogUtils.i("有效加载，没有重复");
                    footView.onFootPrepare();//上拉加载更多
                    footView.onFootViewBegin();//正在加载
                    isLoadSuccess = false;
                }
            }
            if (isLoadComplete) {
                footView.onFootViewAll();
                //LogUtils.i("没有更多数据");
            }
        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            //LogUtils.i("滑动中");
        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
            //LogUtils.i("到底部或者头部了");
        }
    }

    private void initStatus() {
        isLoadStatus = false;
        isLoadComplete = false;
        isLoadSuccess = true;
    }

    /**
     * @param view
     * @param firstVisibleItem 第一个
     * @param visibleItemCount 可见的
     * @param totalItemCount   总数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isLoadMore()) return;
        isLoadComplete = visibleItemCount > totalItemCount;
        if (visibleItemCount < totalItemCount) {//如果当前数据一屏幕内就已经现实完，则表示没有更多数据
            int lastItemIndex = firstVisibleItem + visibleItemCount;
//            LogUtils.i("当前行：" + lastItemIndex);
//            LogUtils.i("第一个：" + firstVisibleItem);
//            LogUtils.i("可见的：" + visibleItemCount);
//            LogUtils.i("总数：" + totalItemCount);
            isLoadStatus = lastItemIndex == totalItemCount;
        }
    }
}
