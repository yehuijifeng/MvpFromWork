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
    private int listStatus;//1代表
    private int itemType = 1;

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
            return baseListActivity.getViewHolder(itemView, position, itemType);
        else
            return baseListFragment.getViewHolder(itemView, position, itemType);
    }

    @Override
    public View getItemView(int position, ViewGroup parent) {
        if (listStatus == ACTIVITY_LIST) {
            View view = baseListActivity.inflater.inflate(baseListActivity.getItemView(position, itemType), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        } else {
            View view = baseListFragment.getActivity().getLayoutInflater().inflate(baseListFragment.getItemView(position, itemType), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        }
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        if (listStatus == ACTIVITY_LIST)
            baseListActivity.getItemData(position, baseViewHolder, itemType);
        else
            baseListFragment.getItemData(position, baseViewHolder, itemType);
    }

    public void closeAdapter() {
        data = null;
        baseListActivity = null;
        baseListFragment = null;
    }
}
