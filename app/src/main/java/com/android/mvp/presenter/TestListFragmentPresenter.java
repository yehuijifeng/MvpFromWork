package com.android.mvp.presenter;

import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.interfaces.ITestListFragment;

/**
 * Created by Luhao on 2016/7/23.
 */
public class TestListFragmentPresenter extends BasePresenter<ITestListFragment> {
    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public TestListFragmentPresenter(ITestListFragment mView) {
        super(mView);
    }
}
