package com.android.mvp.adapter.base;

import android.app.Activity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.mvp.view.activity.base.BaseGridActivity;
import com.android.mvp.view.baseview.FootView;
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
    private FootView footView;//尾部
    private boolean isFooterViewEnable = true;//是否启用尾部视图

    public BaseGridAdapter(int gridStatus, BaseGridActivity baseGridActivity, List data) {
        super(data);
        this.footView = new FootView(baseGridActivity);
        this.gridStatus = gridStatus;
        this.baseGridActivity = baseGridActivity;
        GridView.LayoutParams pl = new GridView.LayoutParams(getDisplayWidth(baseGridActivity),
                GridView.LayoutParams.WRAP_CONTENT);
        footView.setLayoutParams(pl);
    }


    public BaseGridAdapter(int gridStatus, BaseGridFragment baseListFragment, List data) {
        super(data);
        this.footView = new FootView(baseListFragment.getActivity());
        this.gridStatus = gridStatus;
        this.baseGridFragment = baseListFragment;
        GridView.LayoutParams pl = new GridView.LayoutParams(getDisplayWidth(baseListFragment.getActivity()),
                GridView.LayoutParams.WRAP_CONTENT);
        footView.setLayoutParams(pl);
    }

    private int getDisplayWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getWidth();
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

    /**
     * gridview 适配器添加footview的操作
     *
     * */

    /**
     * 当前适配器所显示的行数，最后多添加一个提供给footview用
     *
     * @return
     */
    @Override
    public int getCount() {
        return data.size() - 1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (position == data.size() - 2 && isFooterViewEnable && footView != null) {
            //setFooterViewStatus(FooterView.MORE);
            return footView;
        }

        View v;
        BaseViewHolder baseViewHolder;
        if (view == null) {
            // 说明当前这一行不是重用的
            v = getItemView(position, parent);
            // 加载行布局文件，产生具体的一行
            baseViewHolder = getBaseViewHolder(v, position);
            v.setTag(baseViewHolder);// 将vh存储到行的Tag中
        } else {
            v = view;
            // 取出隐藏在行中的Tag--取出隐藏在这一行中的vh控件缓存对象
            baseViewHolder = (BaseViewHolder) view.getTag();
        }
        getItemData(position, baseViewHolder);
        return v;
    }

}
