package com.android.mvp.presenter;

import com.android.mvp.model.TestSettingsModel;
import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.interfaces.ITestSettingsFragment;

import java.util.Locale;

/**
 * Created by Luhao on 2016/9/12.
 */
public class TestSettingsPresenter extends BasePresenter<ITestSettingsFragment> {
    private TestSettingsModel testSettingsModel;

    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public TestSettingsPresenter(ITestSettingsFragment mView) {
        super(mView);
        testSettingsModel = new TestSettingsModel();
    }


    /**
     * 设置本机语言
     *
     * @param locale 修改的语言
     * @param cla    重启app后跳转的页面
     */
    public void settingLanguage(Locale locale, Class cla) {
        try {
            testSettingsModel.settingLanguage(locale, cla);
            mView.settingLanguage(true);
        } catch (Exception e) {
            mView.settingLanguage(false);
        }
    }

}
