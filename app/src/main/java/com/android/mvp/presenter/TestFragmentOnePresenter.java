package com.android.mvp.presenter;

import com.android.mvp.view.interfaces.base.IBaseView;

/**
 * Created by Luhao on 2016/7/21.
 */
public class TestFragmentOnePresenter extends BasePresenter {

    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public TestFragmentOnePresenter(IBaseView mView) {
        super(mView);
    }
}
