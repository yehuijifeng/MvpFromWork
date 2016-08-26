package com.android.mvp.presenter;

import com.android.mvp.model.TestGridModel;
import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.interfaces.ITestGridView;

import rx.Subscription;

/**
 * Created by Luhao on 2016/8/23.
 */
public class TestGridFragmentPresenter extends BasePresenter<ITestGridView> {
    private TestGridModel model;

    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public TestGridFragmentPresenter(ITestGridView mView) {
        super(mView);
        model = new TestGridModel();
    }

    public Subscription getGoodsList(int number) {
        return model.getGoodsList(number);
    }


}
