package com.android.mvp.presenter;

import android.content.Context;

import com.android.mvp.model.TestListModel;
import com.android.mvp.view.interfaces.ITestListView;

/**
 * Created by Luhao on 2016/7/22.
 */
public class TestListActivityPresenter extends BasePresenter<ITestListView> {

    private TestListModel model;

    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public TestListActivityPresenter(ITestListView mView) {
        super(mView);
        model = new TestListModel();
    }

    public void getGoodsList(Context context, int number) {
        model.getGoodsList(context, number);
    }

}
