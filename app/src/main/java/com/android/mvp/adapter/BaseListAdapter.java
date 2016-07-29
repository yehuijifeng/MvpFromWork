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
        View view;
        if (listStatus == ACTIVITY_LIST) {
            view = baseListActivity.inflater.inflate(baseListActivity.getItemView(position, itemType), parent, false);
        } else {
            view = baseListFragment.getActivity().getLayoutInflater().inflate(baseListFragment.getItemView(position, itemType), parent, false);
        }
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        return view;
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        if (listStatus == ACTIVITY_LIST)
            baseListActivity.getItemData(position, baseViewHolder, itemType);
        else
            baseListFragment.getItemData(position, baseViewHolder, itemType);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rootView;
        if (listStatus == ACTIVITY_LIST) {
            rootView = baseListActivity.getView(position, view, parent);
        } else
            rootView = baseListFragment.getView(position, view, parent);
        if (rootView == null)
            return super.getView(position, view, parent);
        else
            return rootView;
    }

    /**
     * 每一个item的type
     *
     * @param position
     */
    @Override
    public int getItemType(int position) {
        if (listStatus == ACTIVITY_LIST)
            return baseListActivity.getItemType(position);
        else
            return baseListFragment.getItemType(position);
    }

    /**
     * item类型的数量
     * 默认值1；只有一种item类型
     */
    @Override
    public int getTypeCount() {
        /**不明白原因，如果有不同的item，多少种类型都要再此基础上+1，不然abslistview报下标越界异常*/
        if (listStatus == ACTIVITY_LIST)
            return baseListActivity.getTypeCount()+1;
        else
            return baseListFragment.getTypeCount()+1;
    }

    /**
     * 清楚adapter的内存
     */
    public void closeAdapter() {
        data = null;
        baseListActivity = null;
        baseListFragment = null;
    }
}
