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
 * 测试
 * Created by Luhao on 2016/7/21.
 */
public class TestFragmentDialog extends BaseFragment<TestFragmentOnePresenter> implements ITestFragmentOneView, View.OnClickListener {

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
        return R.layout.test_dialog;
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
        loadingDialog = new LoadingDialog(getActivity());
        promptDialog = new PromptDialog(getActivity());
        listDialog = new ListDialog(getActivity());
        pwdDialog = new PwdDialog(getActivity());
        customDialog = new CustomDialog(getActivity());
        //showLoading();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                closeLoading();
//            }
//        }, 1000);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
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
                promptDialog.showPromptDialog("友情提示", "这是一个提示框，用于展示信息", new PromptDialog.OnPromptClickListener() {
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
                listDialog.showListDialog(new String[]{"asdfghhhhhjdfdfddfdfdfdfdfdfdfdf",
                        "123234567765432345765432345456789",
                        "我是一个粉刷的沙发上的士大夫大师傅似的发射点家",
                        "！@#￥%……@#￥%……&*（&……%￥#￥*（……%￥#￥%&*",
                        "12345654ssdfsfd$!@#$%^&*(%稍的沙发上的等"}, new ListDialog.OnListItemClickListener() {
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
                View view = View.inflate(getActivity(), R.layout.test_dialog, null);
                customDialog.showCustomDialog(view, new CustomDialog.OnCustomClickListener() {
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
