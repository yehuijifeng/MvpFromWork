package com.android.mvp.adapter.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Luhao on 2016/6/28.
 * 所有集合view的适配器
 */
public abstract class BaseCollectionAdapter<T> extends BaseAdapter {

    protected List<T> data;//传入的数据

    public BaseCollectionAdapter(List<T> data) {
        this.data = data;
    }

    /**
     * 包含所有的item的子view
     */
    public abstract BaseViewHolder getBaseViewHolder(View itemView, int position);

    /**
     * item的父view
     */
    public abstract View getItemView(int position, ViewGroup parent);

    /**
     * 每一行item需要完成的操作
     */
    public abstract void getItemData(int position, BaseViewHolder baseViewHolder);

    /**
     * 当前适配器所显示的行数
     *
     * @return
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 适配器当前行需要的数据
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 每一个item所返回的id
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
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
