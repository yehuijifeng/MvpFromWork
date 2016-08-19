package com.android.mvp.view.baseview.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.mvp.R;


/**
 * Created by yehuijifeng
 * on 2016/1/8.
 * 提示框
 */
public class PromptDialog extends View implements View.OnClickListener {

    private View root;
    private TextView prompt_title_text, prompt_content_text;
    private Button dialog_default_ok_btn, dialog_default_cancel_btn;
    private ProgressDialog dialog;
    private OnPromptClickListener onPromptClickListener;

    public PromptDialog(Context context) {
        super(context);
    }

    private void initView() {
        root = View.inflate(getContext(), R.layout.dialog_prompt, null);
        prompt_title_text = (TextView) root.findViewById(R.id.prompt_title_text);
        prompt_content_text = (TextView) root.findViewById(R.id.prompt_content_text);
        dialog_default_ok_btn = (Button) root.findViewById(R.id.dialog_default_ok_btn);
        dialog_default_cancel_btn = (Button) root.findViewById(R.id.dialog_default_cancel_btn);
        dialog_default_ok_btn.setOnClickListener(this);
        dialog_default_cancel_btn.setOnClickListener(this);
        dialog = new ProgressDialog(getContext(), R.style.dialog);
        dialog.show();
        dialog.setContentView(root);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_default_ok_btn) {
            dismissPromptDialog();
            onPromptClickListener.onDetermine();
        } else {
            dismissPromptDialog();
            onPromptClickListener.onCancel();
        }
    }

    /**
     * 确定和返回键的回调接口
     */
    public interface OnPromptClickListener {
        void onDetermine();

        void onCancel();
    }

    public void showPromptDialog(String contentStr, OnPromptClickListener onPromptClickListener) {
        showPromptDialog(null, contentStr, null, null, onPromptClickListener);
    }

    public void showPromptDialog(String titleStr, String contentStr, OnPromptClickListener onPromptClickListener) {
        showPromptDialog(null, contentStr, null, null, onPromptClickListener);
    }

    public void showPromptDialog(String titleStr, String contentStr, String btn1, String btn2, OnPromptClickListener onPromptClickListener) {
        this.onPromptClickListener = onPromptClickListener;
        initView();
        if (!TextUtils.isEmpty(titleStr))
            prompt_title_text.setText(titleStr);
        if (!TextUtils.isEmpty(contentStr))
            prompt_content_text.setText(contentStr);
        if (!TextUtils.isEmpty(btn1))
            dialog_default_ok_btn.setText(btn1);
        if (!TextUtils.isEmpty(btn2))
            dialog_default_cancel_btn.setText(btn2);

    }

    /**
     * 关闭dialog
     */
    public void dismissPromptDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    /**
     * 隐藏dialog
     */
    public void hidePromptDialog() {
        if (dialog != null)
            dialog.hide();
    }
}
