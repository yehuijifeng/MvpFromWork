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
    private int itemType = 1;

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
            return baseGridActivity.getViewHolder(itemView, position, itemType);
        else
            return baseGridFragment.getViewHolder(itemView, position, itemType);
    }

    @Override
    public View getItemView(int position, ViewGroup parent) {
        if (gridStatus == ACTIVITY_GRID) {
            View view = baseGridActivity.inflater.inflate(baseGridActivity.getItemView(position, itemType), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        } else {
            View view = baseGridFragment.getActivity().getLayoutInflater().inflate(baseGridFragment.getItemView(position, itemType), parent, false);
            ViewGroup viewGroup = (ViewGroup) view.getRootView();
            viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return view;
        }
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        if (gridStatus == ACTIVITY_GRID)
            baseGridActivity.getItemData(position, baseViewHolder, itemType);
        else
            baseGridFragment.getItemData(position, baseViewHolder, itemType);
    }

    public void closeAdapter() {
        data = null;
        baseGridActivity = null;
        baseGridFragment = null;
    }
}
