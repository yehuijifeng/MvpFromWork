package com.android.mvp.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.adapter.base.BaseViewHolder;
import com.android.mvp.appliaction.MvpAppliaction;
import com.android.mvp.bean.test.GoodsListBean;
import com.android.mvp.http.request.RequestAction;
import com.android.mvp.http.response.ResponseFinalAction;
import com.android.mvp.http.response.ResponseSuccessAction;
import com.android.mvp.presenter.TestListFragmentPresenter;
import com.android.mvp.view.fragment.base.BaseListFragment;
import com.android.mvp.view.interfaces.ITestListFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by Luhao on 2016/7/23.
 */
public class TestListFragment extends BaseListFragment<TestListFragmentPresenter> implements ITestListFragment {
    int pagNumber = 1;

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
    public void getItemData(int position, BaseViewHolder baseViewHolder, int itemType) {
        GoodsListBean goodsListBean = (GoodsListBean) getData().get(position);
        ViewHolder viewHolder = (ViewHolder) baseViewHolder;
        imageLoader.displayImage(goodsListBean.getShopIcon(), viewHolder.test_img, MvpAppliaction.getInstance().defaultOptions);
        viewHolder.test_id_text.setText(goodsListBean.getShopId() + "");
        viewHolder.test_text.setText(goodsListBean.getShopName());
    }

    @Override
    public BaseViewHolder getViewHolder(View itemView, int postion, int itemType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemView(int position, int itemType) {
        return R.layout.item_test;
    }

    @Override
    protected TestListFragmentPresenter initPresenter() {
        return new TestListFragmentPresenter(this);
    }

    @Override
    protected int setFragmentViewContent() {
        return R.layout.activity_test_list;
    }

    @Override
    protected void initData() {
        showLoading();
        setRefresh(false);
        getGoodsList();
        //setRefresh(true);
    }


    private void getGoodsList() {
        Map<String, Object> params = RequestAction.GET_GOODS_LIST.params.getParams();
        params.put("shopInfo.typeId", 10000);
        params.put("shopInfo.index", "pub");
        params.put("pageNum", pagNumber);
        sendRequest(RequestAction.GET_GOODS_LIST);
        //发送请求,返回订阅
        //Subscription subscription = RetrofitManage.getInstance().sendRequest(RequestAction.GET_GOODS_LIST);

//        if (subscription != null && !subscription.isUnsubscribed())
//            //如果订阅取消订阅
//            subscription.unsubscribe();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showLongToast("点击了" + position + "行 id:" + id);
    }

    @Override
    protected void refresh() {
        pagNumber = 1;
        getGoodsList();
    }


    @Override
    public void loadMore() {
        pagNumber++;
        getGoodsList();
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
                break;
        }
    }

    @Override
    protected void onRequestFinal(ResponseFinalAction finals) {
        super.onRequestFinal(finals);
        switch (finals.getRequestAction()) {
            case GET_GOODS_LIST:
                //showLongToast(finals.getErrorMessage());
                loadTextFinal(finals.getErrorMessage() + "，点击重试");
                break;
        }
    }

    @Override
    public void showDataSize(int size) {

    }
}
