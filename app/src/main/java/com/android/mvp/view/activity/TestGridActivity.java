package com.android.mvp.view.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.appliaction.MvpAppliaction;
import com.android.mvp.bean.test.GoodsListBean;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.TestListActivityPresenter;
import com.android.mvp.view.activity.base.BaseGridActivity;
import com.android.mvp.view.interfaces.ITestListView;

import java.util.List;

/**
 * Created by Luhao on 2016/7/22.
 */
public class TestGridActivity extends BaseGridActivity<TestListActivityPresenter> implements ITestListView {

    int pagNumber = 1;

    @Override
    public boolean isRefresh() {
        return true;
    }

    @Override
    public boolean isLoadMore() {
        return true;
    }


//    @Override
//    protected int getHeaderView() {
//        return R.layout.item_test_headler;
//    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getNumColumns() {
        return 3;
    }

    @Override
    public void getItemData(int position, BaseViewHolder baseViewHolder, int itemType) {
        GoodsListBean goodsListBean = (GoodsListBean) getData().get(position);
        if (itemType == 1) {
            ViewHolder viewHolder = (ViewHolder) baseViewHolder;
            imageLoader.displayImage(goodsListBean.getShopIcon(), viewHolder.test_img, MvpAppliaction.getInstance().defaultOptions);
            viewHolder.test_id_text.setText(goodsListBean.getShopId() + "");
            viewHolder.test_text.setText(goodsListBean.getShopName());
        } else {
            ViewHolderTow viewHolderTow = (ViewHolderTow) baseViewHolder;
            //imageLoader.displayImage(goodsListBean.getShopIcon(), viewHolderTow.test_img, MvpAppliaction.getInstance().defaultOptions);
            viewHolderTow.test_img.setText(goodsListBean.getShopIcon() + "");
            viewHolderTow.test_id_text.setText(goodsListBean.getShopId() + "");
            viewHolderTow.test_text.setText(goodsListBean.getShopName());
        }
    }

    @Override
    public BaseViewHolder getViewHolder(View itemView, int postion, int itemType) {
        if (itemType == 1)
            return new ViewHolder(itemView);
        else
            return new ViewHolderTow(itemView);
    }

    @Override
    public int getItemView(int position, int itemType) {
        if (itemType == 1)
            return R.layout.item_test;
        else
            return R.layout.item_test_tow;

    }

    @Override
    protected TestListActivityPresenter initPresenter() {
        return new TestListActivityPresenter(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.test_grid;
    }

    @Override
    protected void initData() {
        showLoading();
        presenter.getGoodsList(pagNumber, 1);
    }

    @Override
    protected String setTitleText() {
        return getResources().getString(R.string.test_grid);
    }

    @Override
    public void onGridViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        showLongToast(getResources().getString(R.string.test_laction) + position + "  id:" + id);
        startActivity(TestListActivity.class);
    }

    @Override
    protected void refresh() {
        pagNumber = 1;
        presenter.getGoodsList(pagNumber, 1);
    }


    @Override
    public void loadMore() {
        pagNumber++;
        presenter.getGoodsList(pagNumber, 1);
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
        private TextView test_id_text, test_text;
        private TextView test_img;

        public ViewHolderTow(View itemView) {
            super(itemView);
        }

        @Override
        public void initItemView(View itemView) {
            test_img = (TextView) itemView.findViewById(R.id.test_img_tow);
            test_id_text = (TextView) itemView.findViewById(R.id.test_id_text_tow);
            test_text = (TextView) itemView.findViewById(R.id.test_text_tow);
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
                goodsListBeans = goodsListBeans.subList(0, 3);
                //goodsListBeans.clear();
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
                loadTextFinal(finals.getErrorMessage());
                break;
        }
    }
}
