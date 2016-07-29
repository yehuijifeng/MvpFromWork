package com.android.mvp.view.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.appliaction.MvpAppliaction;
import com.android.mvp.bean.test.GoodsListBean;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.TestListActivityPresenter;
import com.android.mvp.view.activity.base.BaseListActivity;
import com.android.mvp.view.interfaces.ITestListView;

import java.util.List;

/**
 * Created by Luhao on 2016/7/22.
 */
public class TestListActivity extends BaseListActivity<TestListActivityPresenter> implements ITestListView {

    int pagNumber = 1;
    private final int ITEM_TYPE_TEXT = 1;
    private final int ITEM_TYPE_BTN = 2;

    @Override
    public boolean isRefresh() {
        return true;
    }

    @Override
    public boolean isLoadMore() {
        return true;
    }

    @Override
    protected boolean getIsFootViewClick() {
        return false;
    }

    @Override
    protected boolean getIsHeaderViewClick() {
        return false;
    }

    @Override
    protected int getHeaderView() {
        return R.layout.item_test_tow;
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder, int itemType) {
        GoodsListBean goodsListBean = (GoodsListBean) getData().get(position);
        if (itemType == ITEM_TYPE_TEXT) {
            ViewHolder viewHolder = (ViewHolder) baseViewHolder;
            imageLoader.displayImage(goodsListBean.getShopIcon(), viewHolder.test_img, MvpAppliaction.getInstance().defaultOptions);
            viewHolder.test_id_text.setText(goodsListBean.getShopId() + "");
            viewHolder.test_text.setText(goodsListBean.getShopName());
        } else if (itemType == ITEM_TYPE_BTN) {
            ViewHolderTow viewHolderTow = (ViewHolderTow) baseViewHolder;
            viewHolderTow.test_id_text_tow.setText(goodsListBean.getShopId() + "");
            viewHolderTow.test_text_tow.setText(goodsListBean.getShopName());
            viewHolderTow.test_img_tow.setText(goodsListBean.getShopImg());
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        BaseViewHolder baseViewHolder = null;
        if (getItemType(position) == ITEM_TYPE_TEXT) {
            if (view == null) {
                // 说明当前这一行不是重用的
                v = inflater.inflate(getItemView(position, getItemType(position)), parent, false);
                // 加载行布局文件，产生具体的一行
                baseViewHolder = getViewHolder(v, position, getItemType(position));
                v.setTag(baseViewHolder);// 将vh存储到行的Tag中
            } else {
                v = view;
                // 取出隐藏在行中的Tag--取出隐藏在这一行中的vh控件缓存对象
                baseViewHolder = (BaseViewHolder) view.getTag();
            }
        } else if (getItemType(position) == ITEM_TYPE_BTN) {
            if (view == null) {
                // 说明当前这一行不是重用的
                v = inflater.inflate(getItemView(position, getItemType(position)), parent, false);
                // 加载行布局文件，产生具体的一行
                baseViewHolder = getViewHolder(v, position, getItemType(position));
                v.setTag(baseViewHolder);// 将vh存储到行的Tag中
            } else {
                v = view;
                // 取出隐藏在行中的Tag--取出隐藏在这一行中的vh控件缓存对象
                baseViewHolder = (BaseViewHolder) view.getTag();
            }
        }
        getItemData(position, baseViewHolder, getItemType(position));
        return v;
    }

    @Override
    public int getTypeCount() {
        return 2;
    }

    @Override
    public int getItemType(int position) {
        if (position % 2 == 0) return ITEM_TYPE_BTN;
        return ITEM_TYPE_TEXT;
    }

    @Override
    public BaseViewHolder getViewHolder(View itemView, int postion, int itemType) {
        switch (itemType) {
            case ITEM_TYPE_BTN:
                return new ViewHolderTow(itemView);
            case ITEM_TYPE_TEXT:
                return new ViewHolder(itemView);
            default:
                return new ViewHolder(itemView);
        }
    }

    @Override
    public int getItemView(int position, int itemType) {
        int id = R.layout.item_test;
        switch (itemType) {
            case ITEM_TYPE_BTN:
                id = R.layout.item_test_tow;
                break;
            case ITEM_TYPE_TEXT:
                id = R.layout.item_test;
                break;
        }
        return id;
    }


    @Override
    protected TestListActivityPresenter initPresenter() {
        return new TestListActivityPresenter(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_test_list;
    }

    @Override
    protected void initData() {
        showLoading();
        presenter.getGoodsList(this, pagNumber);
    }

    @Override
    protected String setTitleText() {
        return "列表";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showLongToast("点击了" + position + "行 id:" + id);
    }

    @Override
    protected void refresh() {
        pagNumber = 1;
        presenter.getGoodsList(this, pagNumber);
    }


    @Override
    public void loadMore() {
        pagNumber++;
        presenter.getGoodsList(this, pagNumber);
    }

    class ViewHolder extends BaseViewHolder {
        private TextView test_id_text, test_text;
        private ImageView test_img;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void initItemView(View itemView) {
            test_img = (ImageView) itemView.findViewById(R.id.test_img);
            test_id_text = (TextView) itemView.findViewById(R.id.test_id_text);
            test_text = (TextView) itemView.findViewById(R.id.test_text);
        }
    }

    class ViewHolderTow extends BaseViewHolder {
        private Button test_id_text_tow, test_text_tow;
        private TextView test_img_tow;

        public ViewHolderTow(View itemView) {
            super(itemView);
        }

        @Override
        public void initItemView(View itemView) {
            test_img_tow = (TextView) itemView.findViewById(R.id.test_img_tow);
            test_id_text_tow = (Button) itemView.findViewById(R.id.test_id_text_tow);
            test_text_tow = (Button) itemView.findViewById(R.id.test_text_tow);
        }
    }

    @Override
    protected void onRequestSuccess(ResponseSuccessAction success) {
        super.onRequestSuccess(success);
        switch (success.getRequestAction()) {
            case GET_GOODS_LIST:
                if (pagNumber == 1)
                    clearAll();
                List<GoodsListBean> goodsListBeans = success.getHttpBean().getObjects();
                addAll(goodsListBeans);
                loadSuccess();
                closeLoading();
                break;
        }
    }

    @Override
    protected void onRequestFinal(ResponseFinalAction finals) {
        super.onRequestFinal(finals);
        switch (finals.getRequestAction()) {
            case GET_GOODS_LIST:
                loadFinal();
                showErrorLoading(finals.getErrorMessage(), "刷新");
                break;
        }
    }
}
