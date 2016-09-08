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

    private BaseGridActivity baseGridActivity;//基类activity
    private BaseGridFragment baseGridFragment;//基类fragment
    public final static int FRAGMENT_GRID = 2, ACTIVITY_GRID = 1;//当前实例
    private int gridStatus;//1代表activity,2代表fragment
    public FootView footView;//尾部
    private boolean isFooterViewEnable = true;//是否启用尾部视图
    public final static int FOOT_VIEW = -1, HEANDLER_VIEW = -2;//当前尾部view和头部view的标识
    private View headlerView;//头部
    private int numColumns;

    public boolean isFooterViewEnable() {
        return isFooterViewEnable;
    }

    public void setFooterViewEnable(boolean footerViewEnable) {
        isFooterViewEnable = footerViewEnable;
    }

    private ViewGroup.LayoutParams getDisplayWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        GridView.LayoutParams pl = new GridView.LayoutParams(width,
                GridView.LayoutParams.WRAP_CONTENT);
        return pl;
    }

    public void setGridHeadlerView(View headlerView) {
        this.headlerView = headlerView;
        headlerView.setLayoutParams(getDisplayWidth(gridStatus == ACTIVITY_GRID ? baseGridActivity : baseGridFragment.getActivity()));
    }

    public BaseGridAdapter(int gridStatus, BaseGridActivity baseGridActivity, List data, FootView footView) {
        super(data);
        this.footView = footView;
        this.gridStatus = gridStatus;
        this.baseGridActivity = baseGridActivity;
        numColumns = baseGridActivity.getNumColumns();
    }


    public BaseGridAdapter(int gridStatus, BaseGridFragment baseListFragment, List data, FootView footView) {
        super(data);
        this.footView = footView;
        this.gridStatus = gridStatus;
        this.baseGridFragment = baseListFragment;
        numColumns = baseListFragment.getNumColumns();
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
        if (position == getCount() - 1 && isFooterViewEnable() && footView != null)
            return FOOT_VIEW;
//        else if (headlerView != null && position < numColumns)
//            return HEANDLER_VIEW;
        else if (gridStatus == ACTIVITY_GRID)
            return baseGridActivity.getItemViewType(position);
        else
            return baseGridFragment.getItemViewType(position);
    }

    // 种类+1。这里尤其要注意，必须+1
    @Override
    public int getViewTypeCount() {
        int i = 1;
        if (data.size() > 0) {
            if (isFooterViewEnable()) i++;
            //if (headlerView != null) i++;
        }
        if (gridStatus == ACTIVITY_GRID)
            return baseGridActivity.getViewTypeCount() + i;
        else
            return baseGridFragment.getViewTypeCount() + i;
    }

    /**
     * 清理
     */
    public void closeAdapter() {
        data = null;
        baseGridActivity = null;
        baseGridFragment = null;
        footView = null;
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
            //if (headlerView != null) i += numColumns;
        }
        return data.size() + i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (getItemViewType(position) == FOOT_VIEW) {
            return footView;
        }
//        else if (getItemViewType(position) == HEANDLER_VIEW) {
//            if (position == 0) {
//                return headlerView;
//            } else {
//                View viewNull = new View(parent.getContext());
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                viewNull.setLayoutParams(layoutParams);
//                return viewNull;
//            }
//        }
//        if (headlerView != null)
//            position -= numColumns;
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
