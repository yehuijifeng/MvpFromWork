package com.android.mvp.adapter.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Luhao on 2016/6/28.
 * listview的基类适配器
 */
public class BaseListAdapter extends BaseCollectionAdapter {

    public BaseListAdapter(List data, OnAdapterStatus onAdapterStatus) {
        super(data);
        this.onAdapterStatus = onAdapterStatus;

    }

    private OnAdapterStatus onAdapterStatus;

    public OnAdapterStatus getOnAdapterStatus() {
        return onAdapterStatus;
    }

    public interface OnAdapterStatus {

        BaseViewHolder getViewHolders(View itemView, int position, int type);

        View getItemViews(int position, int type, ViewGroup parent);

        void getItemDatas(int position, BaseViewHolder baseViewHolder, int type);

        int getItemViewTypes(int position);

        int getViewTypeCounts();
    }


    @Override
    public BaseViewHolder getBaseViewHolder(View itemView, int position) {
        return getOnAdapterStatus().getViewHolders(itemView, position, getItemViewType(position));
    }

    @Override
    public View getItemView(int position, ViewGroup parent) {
        View view = getOnAdapterStatus().getItemViews(position, getItemViewType(position), parent).getRootView();
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        return view;
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        getOnAdapterStatus().getItemDatas(position, baseViewHolder, getItemViewType(position));
    }

    public void closeAdapter() {
        data = null;
        onAdapterStatus = null;
    }

    //判断itemView类型,默认0
    @Override
    public int getItemViewType(int position) {
        return getOnAdapterStatus().getItemViewTypes(position);
    }

    // 种类+1。这里尤其要注意，必须+1.具体为什么我也不清楚
    @Override
    public int getViewTypeCount() {
        return getOnAdapterStatus().getViewTypeCounts() + 1;
    }
}
