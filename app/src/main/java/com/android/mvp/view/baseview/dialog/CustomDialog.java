package com.android.mvp.view.baseview.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.mvp.R;


/**
 * Created by yehuijifeng
 * on 2016/1/8.
 * 自定义提示框
 */
public class CustomDialog extends View implements View.OnClickListener {

    private View root;
    private Button dialog_default_ok_btn, dialog_default_cancel_btn;
    private ProgressDialog dialog;
    private CustomOnClickListener customOnClickListener;
    private LinearLayout dialog_custom_layout;

    public CustomDialog(Context context) {
        super(context);
    }

    private void initView() {
        root = View.inflate(getContext(), R.layout.dialog_custom, null);
        dialog_custom_layout = (LinearLayout) root.findViewById(R.id.dialog_custom_layout);
        dialog_default_ok_btn = (Button) root.findViewById(R.id.dialog_default_ok_btn);
        dialog_default_cancel_btn = (Button) root.findViewById(R.id.dialog_default_cancel_btn);
        dialog_default_ok_btn.setOnClickListener(this);
        dialog_default_cancel_btn.setOnClickListener(this);
        dialog = new ProgressDialog(getContext(),R.style.dialog);
        dialog.show();
        dialog.setContentView(root);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_default_ok_btn) {
            dismissCustomDialog();
            customOnClickListener.onDetermine();
        } else {
            dismissCustomDialog();
            customOnClickListener.onCancel();
        }
    }

    /**
     * 确定和返回键的回调接口
     */
    public interface CustomOnClickListener {
        void onDetermine();

        void onCancel();
    }

    public void showCustomDialog(View customView, CustomOnClickListener customOnClickListener) {
        this.customOnClickListener = customOnClickListener;
        initView();
        if (customView != null)
            dialog_custom_layout.addView(customView);
    }


    /**
     * 关闭dialog
     */
    public void dismissCustomDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    /**
     * 隐藏dialog
     */
    public void hideCustomDialog() {
        if (dialog != null)
            dialog.hide();
    }
}
