package com.android.mvp.presenter;

import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.interfaces.ITestFragmetDialogView;

/**
 * Created by Luhao on 2016/7/21.
 */
public class TestFragmentOnePresenter extends BasePresenter<ITestFragmetDialogView> {

    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public TestFragmentOnePresenter(ITestFragmetDialogView mView) {
        super(mView);
    }

    public void setPassword(String md5Str) {
        mView.showPasswordDialog(md5Str);
    }

}
