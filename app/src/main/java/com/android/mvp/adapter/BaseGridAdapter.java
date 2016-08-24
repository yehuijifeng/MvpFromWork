package com.android.mvp.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.adapter.base.BaseCollectionAdapter;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.view.activity.base.BaseGridActivity;
import com.android.mvp.view.fragment.base.BaseGridFragment;

import java.util.List;

/**
 * Created by Luhao on 2016/6/28.
 * listview的基类适配器
 */
public class BaseGridAdapter extends BaseCollectionAdapter {

    private BaseGridActivity baseGridActivity;
    private BaseGridFragment baseGridFragment;
    public final static int FRAGMENT_GRID = 2, ACTIVITY_GRID = 1;
    private int gridStatus;//1代表activity,2代表fragment

    public BaseGridAdapter(int gridStatus, BaseGridActivity baseGridActivity, List data) {
        super(data);
        this.gridStatus = gridStatus;
        this.baseGridActivity = baseGridActivity;
    }

    public BaseGridAdapter(int gridStatus, BaseGridFragment baseListFragment, List data) {
        super(data);
        this.gridStatus = gridStatus;
        this.baseGridFragment = baseListFragment;
    }

    @Override
    public BaseViewHolder getBaseViewHolder(View itemView, int position) {
        if (gridStatus == ACTIVITY_GRID)
            return baseGridActivity.getViewHolder(itemView, position, getItemViewType(position));
        else
            return baseGridFragment.getViewHolder(itemView, position, getItemViewType(position));
    }

    @Override
    public View getItemView(int position, ViewGroup parent) {
        if (gridStatus == ACTIVITY_GRID) {
            View view = baseGridActivity.inflater.inflate(baseGridActivity.getItemView(position, getItemViewType(position)), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        } else {
            View view = baseGridFragment.getActivity().getLayoutInflater().inflate(baseGridFragment.getItemView(position, getItemViewType(position)), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        }
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        if (gridStatus == ACTIVITY_GRID)
            baseGridActivity.getItemData(position, baseViewHolder, getItemViewType(position));
        else
            baseGridFragment.getItemData(position, baseViewHolder, getItemViewType(position));
    }

    //判断itemView类型,默认0
    @Override
    public int getItemViewType(int position) {
        if (gridStatus == ACTIVITY_GRID)
            return baseGridActivity.getItemViewType(position);
        else
            return baseGridFragment.getItemViewType(position);
    }

    // 种类+1。这里尤其要注意，必须+1
    @Override
    public int getViewTypeCount() {
        if (gridStatus == ACTIVITY_GRID)
            return baseGridActivity.getViewTypeCount() + 1;
        else
            return baseGridFragment.getViewTypeCount() + 1;
    }

    public void closeAdapter() {
        data = null;
        baseGridActivity = null;
        baseGridFragment = null;
    }


}
