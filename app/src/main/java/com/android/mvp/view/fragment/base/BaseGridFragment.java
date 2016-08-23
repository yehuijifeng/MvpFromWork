package com.android.mvp.view.fragment.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.mvp.R;
import com.android.mvp.adapter.BaseGridAdapter;
import com.android.mvp.adapter.BaseListAdapter;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.presenter.BasePresenter;
import com.android.mvp.view.baseview.BaseGridview;
import com.android.mvp.view.baseview.BaseListView;
import com.android.mvp.view.baseview.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luhao on 2016/8/22.
 */
public abstract class BaseGridFragment<T extends BasePresenter> extends BaseFragment<T> implements ListView.OnItemClickListener {

    private BaseGridview baseGridview;
    private MyGridView myGridView;
    protected List<Object> data = new ArrayList<>();
    protected BaseGridAdapter baseGridAdapter;
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
    protected void initView(View parentView) {
        baseGridview = (BaseGridview) parentView.findViewById(R.id.default_grid_view);
        myGridView = baseGridview.myGridView;
        if (getHeaderView() > 0) {
            View view = View.inflate(getActivity(), getHeaderView(), null);
            if (view != null) {
                ViewGroup viewGroup = (ViewGroup) view.getRootView();
                viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                listView.addHeaderView(view, null, getIsHeaderViewClick());
            }
        }
        baseListAdapter = new BaseListAdapter(BaseListAdapter.FRAGMENT_LIST, this, data);
        listView.setAdapter(baseListAdapter);
        listView.setOnItemClickListener(this);
        listView.addFooterView(baseListView.footView, null, getIsFootViewClick());
        baseListView.setRefresh(isRefresh());
        baseListView.setLoadMore(isLoadMore());
        defaultRefresh();
        defaultLoadMore();
    }

    protected void getGridViewHour(int number) {

    }


}
