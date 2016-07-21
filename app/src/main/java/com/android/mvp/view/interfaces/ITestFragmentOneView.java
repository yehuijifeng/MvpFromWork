package com.android.mvp.view.interfaces;

import com.android.mvp.view.interfaces.base.IBaseView;

/**
 * Created by Luhao on 2016/7/21.
 */
public interface ITestFragmentOneView extends IBaseView {

    void showPasswordDialog(String pwd);

    void showListDialogItem(int postion, String content);

}
