package com.android.mvp.view.activity.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.mvp.R;
import com.android.mvp.adapter.base.BaseGridAdapter;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.http.StatusCode;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.baseview.BaseGridview;
import com.android.mvp.view.baseview.FootView;
import com.android.mvp.view.baseview.HeaderView;
import com.android.mvp.view.baseview.MyGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Luhao on 2016/8/22.
 * gridview的activity的基类
 */
public abstract class BaseGridActivity<T extends BasePresenter> extends BaseActivity<T> implements GridView.OnItemClickListener {

    private BaseGridview baseGridview;
    private MyGridView myGridView;
    protected List<Object> data = new ArrayList<>();
    protected BaseGridAdapter baseGridAdapter;
    protected boolean isRefresh = true, isLoadMore = true;
    protected int numColumns = 2;//默认gridview一行包含两个视图

    public boolean isRefresh() {
        return isRefresh;
    }

    /**
     * 是否下拉刷新
     */
    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
        if (baseGridview != null) baseGridview.setRefresh(refresh);
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    /**
     * 是否上拉加载
     */
    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
        if (baseGridview != null) baseGridview.setLoadMore(loadMore);
        if (baseGridAdapter != null) baseGridAdapter.setFooterViewEnable(isLoadMore());
    }

    /**
     * 每一行item的数据
     *
     * @param position
     * @param baseViewHolder
     * @param itemType
     */
    public abstract void getItemData(int position, BaseViewHolder baseViewHolder, int itemType);

    /**
     * 返回一个提供重用的viewholder
     *
     * @param itemView
     * @param postion
     * @param itemType
     * @return
     */
    public abstract BaseViewHolder getViewHolder(View itemView, int postion, int itemType);

    /**
     * 返回一个item的视图
     *
     * @param position
     * @return
     */
    public abstract int getItemView(int position, int itemType);

    /**
     * 传入头view
     */
    protected int getHeaderView() {
        return 0;
    }

    /**
     * listview的头view是否可点击
     * 默认，false
     */
    protected boolean getIsHeaderViewClick() {
        return false;
    }

    //判断itemView类型,默认0
    public int getItemViewType(int position) {
        return 0;
    }

    // 种类+1。这里尤其要注意，必须+1.具体为什么我也不清楚
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    protected void initView() {
        baseGridview = (BaseGridview) findViewById(R.id.default_grid_view);
        myGridView = baseGridview.myGridView;
        if (getHeaderView() > 0) {
            View view = View.inflate(this, getHeaderView(), null);
            if (view != null) {
                ViewGroup viewGroup = (ViewGroup) view.getRootView();
                viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                //myGridView.addHeaderView(view, null, getIsHeaderViewClick());
            }
        }
        baseGridAdapter = new BaseGridAdapter(BaseGridAdapter.ACTIVITY_GRID, this, data, baseGridview.footView);
        myGridView.setAdapter(baseGridAdapter);
        myGridView.setOnItemClickListener(this);
        // myGridView.addFooterView(baseListView.footView, null, getIsFootViewClick());
        myGridView.setNumColumns(getNumColumns());
        baseGridview.setRefresh(isRefresh());
        baseGridview.setLoadMore(isLoadMore());
        baseGridAdapter.setFooterViewEnable(isLoadMore());
        defaultRefresh();
        defaultLoadMore();
    }

    protected int getNumColumns() {
        return numColumns;
    }

    /**
     * 重新设置gridview一行中view的个数
     *
     * @param numColumns
     */
    protected void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        if (myGridView != null) myGridView.setNumColumns(numColumns);
        notifyDataChange();
    }


    @Override
    protected void onRequestSuccess(ResponseSuccessAction success) {
        if (success.getRequestCode() == StatusCode.REQUEST_SUCCESS) {
            baseGridview.setOnExecuteScollSuccess();
            baseGridview.footView.onFootPrepare(baseGridview.isLoadComplete());
        }
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    @Override
    protected void onRequestFinal(ResponseFinalAction finals) {
        if (finals.getRequestCode() == StatusCode.NOT_MORE_DATA) {
            baseGridview.footView.onFootViewAll();
        }
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    /**
     * 获得listview中的数据
     */
    protected List getData() {
        return data;
    }


    /**
     * 加载更多的事件，子类可以重写
     */
    public void loadMore() {

    }

    /**
     * 刷新数据
     */
    protected void notifyDataChange() {
        baseGridAdapter.notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    protected void addAll(Collection datas) {
        if (datas == null) return;
        data.addAll(datas);
    }

    /**
     * 删除数据
     */
    protected void clearAll() {
        if (data == null) return;
        data.clear();
    }

    /**
     * 下拉刷新监听事件
     */
    private void defaultRefresh() {
        baseGridview.setRefreshListener(new HeaderView.RefreshListener() {
            @Override
            public void onRefreshPrepare(boolean bl, PtrFrameLayout frame) {
                //准备刷新
            }

            @Override
            public void onRefreshBegin(boolean bl, PtrFrameLayout frame) {
                //刷新中
                if (isRefresh())
                    refresh();
            }

            @Override
            public void onRefreshComplete(boolean bl, PtrFrameLayout frame) {
                //刷新完成
            }
        });
    }

    /**
     * 上拉加载监听
     */
    private void defaultLoadMore() {
        baseGridview.setLoadMoreListener(new FootView.LoadMoreListener() {
            @Override
            public void onLoadMorePrepare(boolean bl) {
                //准备加载
            }

            @Override
            public void onLoadMoreBegin(boolean bl) {
                //开始加载
                if (isLoadMore())
                    loadMore();
            }

            @Override
            public void onLoadMoreComplete(boolean bl) {
                //加载完全部数据

            }
        });
    }

    protected void loadSuccess() {
        notifyDataChange();
        closeLoading();
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    protected void loadTextFinal(String errorStr) {
        showErrorLoadingByDefaultClick(errorStr);
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    protected void loadTextFinal(String errorStr, View.OnClickListener onClickListener) {
        showErrorLoading(errorStr, onClickListener);
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    protected void loadBtnFinal(String errorStr, String btnStr) {
        showErrorBtnLoadingByDefaultClick(errorStr, btnStr);
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    protected void loadBtnFinal(String errorStr, String btnStr, View.OnClickListener onClickListener) {
        showErrorBtnLoading(errorStr, btnStr, onClickListener);
        if (isRefresh())
            baseGridview.closeRefreshView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baseGridAdapter.closeAdapter();
    }
}
