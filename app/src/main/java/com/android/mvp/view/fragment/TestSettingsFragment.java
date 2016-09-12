package com.android.mvp.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.presenter.TestSettingsPresenter;
import com.android.mvp.view.activity.TestViewpagerActivity;
import com.android.mvp.view.fragment.base.BaseFragment;
import com.android.mvp.view.interfaces.ITestSettingsFragment;

import java.util.Locale;

/**
 * Created by Luhao on 2016/9/12.
 */
public class TestSettingsFragment extends BaseFragment<TestSettingsPresenter> implements ITestSettingsFragment, View.OnClickListener {

    private TextView language_text, skin_text;

    @Override
    protected TestSettingsPresenter initPresenter() {
        return new TestSettingsPresenter(this);
    }

    @Override
    protected int setFragmentViewContent() {
        return R.layout.test_settings;
    }

    @Override
    protected void initView(View parentView) {
        language_text = (TextView) parentView.findViewById(R.id.language_text);
        skin_text = (TextView) parentView.findViewById(R.id.skin_text);
    }

    @Override
    protected void initData() {
        language_text.setOnClickListener(this);
        skin_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.language_text://设置语言
                presenter.settingLanguage(getActivity(), Locale.ENGLISH, TestViewpagerActivity.class);
                break;
            case R.id.skin_text://设置皮肤
                break;
        }
    }

    @Override
    public void settingLanguage(boolean bl) {
        showShortToast("修改语言   " + (bl ? "成功" : "失败"));
    }
}
