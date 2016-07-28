package com.android.mvp.view.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.mvp.R;
import com.android.mvp.presenter.TestFragmentOnePresenter;
import com.android.mvp.view.baseview.dialog.CustomDialog;
import com.android.mvp.view.baseview.dialog.ListDialog;
import com.android.mvp.view.baseview.dialog.LoadingDialog;
import com.android.mvp.view.baseview.dialog.PromptDialog;
import com.android.mvp.view.baseview.dialog.PwdDialog;
import com.android.mvp.view.fragment.base.BaseFragment;
import com.android.mvp.view.interfaces.ITestFragmentOneView;

/**
 * Created by Luhao on 2016/7/21.
 */
public class TestFragmentOne extends BaseFragment<TestFragmentOnePresenter> implements ITestFragmentOneView, View.OnClickListener {

    private Button loading_btn, tishi_btn, zidingyi_btn, list_btn, pwd_btn;

    private LoadingDialog loadingDialog;//loading
    private PromptDialog promptDialog;//提示
    private ListDialog listDialog;//列表
    private PwdDialog pwdDialog;//密码
    private CustomDialog customDialog;//自定义

    @Override
    protected TestFragmentOnePresenter initPresenter() {
        return new TestFragmentOnePresenter(this);
    }

    @Override
    protected int setFragmentViewContent() {
        return R.layout.fragment_test_one;
    }

    @Override
    protected void initView(View parentView) {
        loading_btn = (Button) parentView.findViewById(R.id.loading_btn);
        tishi_btn = (Button) parentView.findViewById(R.id.tishi_btn);
        zidingyi_btn = (Button) parentView.findViewById(R.id.zidingyi_btn);
        list_btn = (Button) parentView.findViewById(R.id.list_btn);
        pwd_btn = (Button) parentView.findViewById(R.id.pwd_btn);

        loading_btn.setOnClickListener(this);
        tishi_btn.setOnClickListener(this);
        zidingyi_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        pwd_btn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(parentActivity);
        promptDialog = new PromptDialog(parentActivity);
        listDialog = new ListDialog(parentActivity);
        pwdDialog = new PwdDialog(parentActivity);
        customDialog = new CustomDialog(parentActivity);
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeLoading();
            }
        }, 3000);
    }

    @Override
    public void showPasswordDialog(String pwd) {
        showShortToast(pwd);
    }

    @Override
    public void showListDialogItem(int postion, String content) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loading_btn:
                loadingDialog.showLoadingDialog("正在加载中");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissLoadingDialog();
                    }
                }, 3000);
                break;
            case R.id.tishi_btn:
                promptDialog.showPromptDialog("友情提示", "这是一个提示框，用于展示信息", new PromptDialog.PromptOnClickListener() {
                    @Override
                    public void onDetermine() {
                        showShortToast("确定");
                    }

                    @Override
                    public void onCancel() {
                        showShortToast("取消");
                    }
                });
                break;
            case R.id.list_btn:
                listDialog.showListDialog(new String[]{"视频电话", "QQ电话", "拨打电话"}, new ListDialog.ListOnClickListener() {
                    @Override
                    public void onCancel() {
                        showShortToast("取消");
                    }

                    @Override
                    public void onItems(int item, String itemName) {
                        showShortToast("点击了第" + item + "个，内容：" + itemName);
                    }
                });
                break;
            case R.id.zidingyi_btn:
                View view = View.inflate(parentActivity, R.layout.fragment_test_one, null);
                customDialog.showCustomDialog(view, new CustomDialog.CustomOnClickListener() {
                    @Override
                    public void onDetermine() {
                        showShortToast("确定");
                    }

                    @Override
                    public void onCancel() {
                        showShortToast("取消");
                    }
                });
                break;
            case R.id.pwd_btn:
                pwdDialog.showPwdDialog("请输入六位数的密码", new PwdDialog.PwdDialogListener() {
                    @Override
                    public void onDetermine(String password) {
                        presenter.setPassword(password);

                    }

                    @Override
                    public void onCancel() {
                        showShortToast("取消");
                    }
                });
                break;
        }
    }
}
