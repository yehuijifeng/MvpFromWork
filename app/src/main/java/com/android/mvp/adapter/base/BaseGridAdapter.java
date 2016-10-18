package com.android.mvp.adapter.base;

import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.view.baseview.FootView;

import java.util.List;

/**
 * Created by Luhao on 2016/6/28.
 * gridview的基类适配器
 */
public class BaseGridAdapter extends BaseCollectionAdapter {

    public FootView footView;//尾部
    private boolean isFooterViewEnable = true;//是否启用尾部视图
    public final static int FOOT_VIEW = -1, HEANDLER_VIEW = -2;//当前尾部view和头部view的标识
    private View headlerView;//头部
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


    public boolean isFooterViewEnable() {
        return isFooterViewEnable;
    }

    public void setFooterViewEnable(boolean footerViewEnable) {
        isFooterViewEnable = footerViewEnable;
    }

    public void setGridHeadlerView(View headlerView) {
        this.headlerView = headlerView;
    }

    public BaseGridAdapter(List data, FootView footView, OnAdapterStatus onAdapterStatus) {
        super(data);
        this.footView = footView;
        this.onAdapterStatus = onAdapterStatus;
    }

    @Override
    public BaseViewHolder getBaseViewHolder(View itemView, int position) {
        return getOnAdapterStatus().getViewHolders(itemView, position, getItemViewType(position));
    }

    @Override
    public View getItemView(int position, ViewGroup parent) {
        View view = getOnAdapterStatus().getItemViews(position, getItemViewType(position), parent);
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        return view;
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder) {
        getOnAdapterStatus().getItemDatas(position, baseViewHolder, getItemViewType(position));
    }

    //判断itemView类型,默认0
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1 && isFooterViewEnable() && footView != null)
            return FOOT_VIEW;
        return getOnAdapterStatus().getItemViewTypes(position);
    }

    // 种类+1。这里尤其要注意，必须+1
    @Override
    public int getViewTypeCount() {
        int i = 1;
        if (data.size() > 0) {
            if (isFooterViewEnable()) i++;
        }
        return getOnAdapterStatus().getViewTypeCounts() + i;
    }

    /**
     * 清理
     */
    public void closeAdapter() {
        data = null;
        footView = null;
        onAdapterStatus = null;
    }

    /**
     * 当前适配器所显示的行数，最后多添加一个提供给footview用
     *
     * @return
     */
    @Override
    public int getCount() {
        int i = 0;
        if (data.size() > 0) {
            if (isFooterViewEnable()) i++;
        }
        return data.size() + i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (getItemViewType(position) == FOOT_VIEW) {
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
