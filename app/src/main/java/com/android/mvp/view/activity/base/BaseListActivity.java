package com.android.mvp.view.activity.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.mvp.R;
import com.android.mvp.adapter.BaseListAdapter;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.http.StatusCode;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.baseview.BaseListView;
import com.android.mvp.view.baseview.FootView;
import com.android.mvp.view.baseview.HeaderView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Luhao on 2016/7/22.
 */
public abstract class BaseListActivity<T extends BasePresenter> extends BaseActivity<T> implements ListView.OnItemClickListener {

    protected BaseListView baseListView;
    public ListView listView;
    protected List<Object> data = new ArrayList<>();
    protected BaseListAdapter baseListAdapter;
    protected boolean isRefresh = true, isLoadMore = true;

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

    @Override
    public void initView() {
        baseListView = (BaseListView) findViewById(R.id.default_list_view);
        listView = baseListView.listView;
        if (getHeaderView() > 0) {
            View view = inflater.inflate(getHeaderView(), null);
            if (view != null) {
                ViewGroup viewGroup = (ViewGroup) view.getRootView();
                viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                listView.addHeaderView(view, null, getIsHeaderViewClick());
            }
        }
        baseListAdapter = new BaseListAdapter(BaseListAdapter.ACTIVITY_LIST, this, data);
        listView.setAdapter(baseListAdapter);
        listView.setOnItemClickListener(this);
        listView.addFooterView(baseListView.footView, null, getIsFootViewClick());
        baseListView.setRefresh(isRefresh());
        baseListView.setLoadMore(isLoadMore());
        defaultRefresh();
        defaultLoadMore();
    }

    @Override
    protected void onRequestSuccess(ResponseSuccessAction success) {
        if (success.getRequestCode() == StatusCode.REQUEST_SUCCESS) {
            baseListView.setOnExecuteScoll(true);
            baseListView.footView.onFootPrepare();
        }
        if (isRefresh())
            baseListView.closeRefreshView();
    }

    @Override
    protected void onRequestFinal(ResponseFinalAction finals) {
        if (finals.getRequestCode() == StatusCode.NOT_MORE_DATA) {
            baseListView.footView.onFootViewAll();
        }
        if (isRefresh())
            baseListView.closeRefreshView();
    }

    /**
     * 传入头view
     */
    protected int getHeaderView() {
        return 0;
    }

    /**
     * listview的头view是否可点击
     * 默认，true
     */
    protected boolean getIsHeaderViewClick() {
        return false;
    }

    /**
     * 重写listview的尾view
     */
    protected void getAgainFootView(View footView) {
        listView.removeFooterView(baseListView.footView);
        listView.addFooterView(footView, null, getIsFootViewClick());
    }

    /**
     * listview的尾view是否可点击
     * 默认，true
     */
    protected boolean getIsFootViewClick() {
        return false;
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
        baseListAdapter.notifyDataSetChanged();
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
     * 是否加载更多
     */
    protected boolean isLoadMore() {
        return isLoadMore;
    }

    protected void setLoadMore(boolean bl) {
        isLoadMore = bl;
        if (baseListView != null) baseListView.setLoadMore(bl);
    }

    /**
     * 是否下拉刷新
     */
    protected boolean isRefresh() {
        return isRefresh;
    }

    protected void setRefresh(boolean bl) {
        isRefresh = bl;
        if (baseListView != null) baseListView.setRefresh(bl);
    }

    /**
     * 下拉刷新监听事件
     */
    private void defaultRefresh() {
        baseListView.setRefreshListener(new HeaderView.RefreshListener() {
            @Override
            public void onRefreshPrepare(boolean bl, PtrFrameLayout frame) {
                //准备刷新
            }

            @Override
            public void onRefreshBegin(boolean bl, PtrFrameLayout frame) {
                //刷新中
                refresh();
            }

            @Override
            public void onRefreshComplete(boolean bl, PtrFrameLayout frame) {
                //刷新完成
            }
        });
    }

    /**
     * 下拉加载监听
     */
    private void defaultLoadMore() {
        baseListView.setLoadMoreListener(new FootView.LoadMoreListener() {
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
        if (isRefresh())
            baseListView.closeRefreshView();
    }

    protected void loadFinal() {
        if (isRefresh())
            baseListView.closeRefreshView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseListAdapter.closeAdapter();
    }
}
