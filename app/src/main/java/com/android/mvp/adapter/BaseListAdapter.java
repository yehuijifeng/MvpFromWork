//package com.android.mvp.adapter;
//
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.android.mvp.adapter.base.BaseCollectionAdapter;
//import com.android.mvp.adapter.base.BaseViewHolder;
//
//import java.util.List;
//
///**
// * Created by Luhao on 2016/6/28.
// * listview的基类适配器
// */
//public class BaseListAdapter extends BaseCollectionAdapter {
//
//    private BaseListActivity baseListActivity;
//
//    public BaseListAdapter(BaseListActivity baseListActivity, List data) {
//        super(data);
//        this.baseListActivity = baseListActivity;
//    }
//
//    @Override
//    public BaseViewHolder getBaseViewHolder(View itemView, int position) {
//        return baseListActivity.getViewHolder(itemView, position);
//    }
//
//    @Override
//    public View getItemView(int position, ViewGroup parent) {
//        return baseListActivity.inflater.inflate(baseListActivity.getItemView(position), parent, false);
//    }
//
//    @Override
//    public void getItemData(int position, BaseViewHolder baseViewHolder) {
//        baseListActivity.getItemData(position, baseViewHolder);
//    }
//
//    public void closeAdapter() {
//        baseListActivity = null;
//        data = null;
//    }
//}
