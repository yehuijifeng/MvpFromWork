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
    private PromptOnClickListener promptOnClickListener;

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
        dialog = new ProgressDialog(getContext());
        dialog.show();
        dialog.setContentView(root);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_default_ok_btn) {
            dismissPromptDialog();
            promptOnClickListener.onDetermine();
        } else {
            dismissPromptDialog();
            promptOnClickListener.onCancel();
        }
    }

    /**
     * 确定和返回键的回调接口
     */
    public interface PromptOnClickListener {
        void onDetermine();

        void onCancel();
    }

    public void showPromptDialog(PromptOnClickListener promptOnClickListener) {
        showPromptDialog(null, null, promptOnClickListener);
    }

    public void showPromptDialog(String contentStr, PromptOnClickListener promptOnClickListener) {
        showPromptDialog(null,contentStr, promptOnClickListener);
    }

    public void showPromptDialog(String titleStr, String contentStr, PromptOnClickListener promptOnClickListener) {
        this.promptOnClickListener = promptOnClickListener;
        initView();
        if (!TextUtils.isEmpty(titleStr))
            prompt_title_text.setText(titleStr);
        if (!TextUtils.isEmpty(contentStr))
            prompt_content_text.setText(contentStr);
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
