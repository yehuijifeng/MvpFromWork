package com.android.mvp.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.adapter.base.BaseCollectionAdapter;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.view.activity.base.BaseListActivity;
import com.android.mvp.view.fragment.base.BaseListFragment;

import java.util.List;

/**
 * Created by Luhao on 2016/6/28.
 * listview的基类适配器
 */
public class BaseListAdapter extends BaseCollectionAdapter {

    private BaseListActivity baseListActivity;
    private BaseListFragment baseListFragment;
    public final static int FRAGMENT_LIST = 2, ACTIVITY_LIST = 1;
    private int listStatus;//1代表activity,2代表fragment

    public BaseListAdapter(int listStatus, BaseListActivity baseListActivity, List data) {
        super(data);
        this.listStatus = listStatus;
        this.baseListActivity = baseListActivity;
    }

    public BaseListAdapter(int listStatus, BaseListFragment baseListFragment, List data) {
        super(data);
        this.listStatus = listStatus;
        this.baseListFragment = baseListFragment;
    }

    @Override
    public BaseViewHolder getBaseViewHolder(View itemView, int position) {
        if (listStatus == ACTIVITY_LIST)
            return baseListActivity.getViewHolder(itemView, position, getItemViewType(position));
        else
            return baseListFragment.getViewHolder(itemView, position, getItemViewType(position));
    }

    @Override
    public View getItemView(int position, ViewGroup parent) {
        if (listStatus == ACTIVITY_LIST) {
            View view = baseListActivity.inflater.inflate(baseListActivity.getItemView(position, getItemViewType(position)), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        } else {
            View view = baseListFragment.getActivity().getLayoutInflater().inflate(baseListFragment.getItemView(position, getItemViewType(position)), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        }
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        if (listStatus == ACTIVITY_LIST)
            baseListActivity.getItemData(position, baseViewHolder, getItemViewType(position));
        else
            baseListFragment.getItemData(position, baseViewHolder, getItemViewType(position));
    }

    public void closeAdapter() {
        data = null;
        baseListActivity = null;
        baseListFragment = null;
    }

    //判断itemView类型,默认0
    @Override
    public int getItemViewType(int position) {
        if (listStatus == ACTIVITY_LIST)
            return baseListActivity.getItemViewType(position);
        else
            return baseListFragment.getItemViewType(position);
    }

    // 种类+1。这里尤其要注意，必须+1.具体为什么我也不清楚
    @Override
    public int getViewTypeCount() {
        if (listStatus == ACTIVITY_LIST)
            return baseListActivity.getViewTypeCount() + 1;
        else
            return baseListFragment.getViewTypeCount() + 1;
    }
}
