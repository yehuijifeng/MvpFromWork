package com.android.mvp.presenter;

import com.android.mvp.model.TestSettingsModel;
import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.interfaces.ITestSettingsFragment;
import com.android.skin.interfaces.ISkinCallback;

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

    /**
     * 更换内部资源，根据后缀名
     *
     * @param skinType 后缀名
     */
    public void changeInternalSkinByPostfix(String skinType) {
        testSettingsModel.changeInternalSkinByPostfix(skinType);
    }

    /**
     * 更换外部资源，定位绝对路径，同app内部原生资源名相同
     *
     * @param skinPath    资源包绝对路径
     * @param skinBackage 资源包包名
     */
    public void changeExternalSkin(String skinPath, String skinBackage) {
        testSettingsModel.changeExternalSkin(skinPath, skinBackage, new ISkinCallback() {
            @Override
            public void onStart() {
                mView.changeSkinStart();
            }

            @Override
            public void onError(Exception e) {
                mView.changeSkinError(e);
            }

            @Override
            public void onComplete() {
                mView.changeSkinComplete();
            }
        });
    }

    /**
     * 更换外部资源，定位绝对路径，同app内部原生资源名的拓展名相同
     *
     * @param skinPath    资源包绝对路径
     * @param skinBackage 资源包包名
     * @param Postfix     资源拓展名
     */
    public void changeExternalSkinByPostfix(String skinPath, String skinBackage, String Postfix) {
        testSettingsModel.changeExternalSkinByPostfix(skinPath, skinBackage, Postfix, new ISkinCallback() {
            @Override
            public void onStart() {
                mView.changeSkinStart();
            }

            @Override
            public void onError(Exception e) {
                mView.changeSkinError(e);
            }

            @Override
            public void onComplete() {
                mView.changeSkinComplete();
            }
        });
    }
}
