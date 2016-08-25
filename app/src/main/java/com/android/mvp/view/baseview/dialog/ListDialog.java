package com.android.mvp.view.baseview.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.utils.DisplayUtils;


/**
 * Created by yehuijifeng
 * on 2016/1/8.
 * 菜单列表提示框
 */
public class ListDialog extends View implements View.OnClickListener {

    private View root;
    private ProgressDialog alertDialog;
    private LinearLayout list_layout;
    private OnListItemClickListener onListItemClickListener;
    private LinearLayout list_exit_layout;
    private LinearLayout.LayoutParams layoutParams;
    View itemView;
    TextView textView;
    private TextView list_exit_text;

    public ListDialog(Context context) {
        super(context);
    }

    private void initView() {
        root = View.inflate(getContext(), R.layout.dialog_list, null);
        root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissListDialog();
            }
        });
        list_layout = (LinearLayout) root.findViewById(R.id.list_layout);
        list_exit_layout = (LinearLayout) root.findViewById(R.id.list_exit_layout);
        list_exit_layout.setOnClickListener(this);
        list_exit_text = (TextView) root.findViewById(R.id.list_exit_text);
        alertDialog = new ProgressDialog(getContext(), R.style.dialog);
    }

    /**
     * 确定和返回键的回调接口
     */
    public interface OnListItemClickListener {
        void onCancel();

        void onItems(int item, String itemName);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.list_exit_layout) {
            dismissListDialog();
            onListItemClickListener.onCancel();
        }
    }

    class onItemClick implements OnClickListener {
        private int item;
        private String itemName;

        onItemClick(int item, String itemName) {
            this.item = item;
            this.itemName = itemName;
        }

        @Override
        public void onClick(View v) {
            dismissListDialog();
            onListItemClickListener.onItems(item, itemName);
        }
    }

    public void showListDialog(String[] itemStr, OnListItemClickListener onListItemClickListener) {
        showListDialog(itemStr, null, onListItemClickListener);
    }

    public void showListDialog(String[] itemStr, String closeStr, OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
        initView();
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = DisplayUtils.dip2px(getContext(), 1);
        for (int i = 0; i < itemStr.length; i++) {
            if (itemStr.length == 1) {
                itemAll(itemStr[i], i);
            } else {
                if (i == 0) {
                    itemTop(itemStr[i], i);
                } else if (i == itemStr.length - 1) {
                    itemBottom(itemStr[i], i);
                } else {
                    itemDefault(itemStr[i], i);
                }
            }
        }
        if (!TextUtils.isEmpty(closeStr))
            list_exit_text.setText(closeStr);
        alertDialog.show();
        alertDialog.setContentView(root);
    }

    /**
     * item 默认样式
     *
     * @param itemStr
     * @param itemId
     */
    private void itemDefault(String itemStr, int itemId) {
        itemView = View.inflate(getContext(), R.layout.dialog_list_item, null);
        textView = (TextView) itemView.findViewById(R.id.list_item_text);
        textView.setText(itemStr);
        itemView.setLayoutParams(layoutParams);
        list_layout.addView(itemView);
        itemView.setOnClickListener(new onItemClick(itemId, itemStr));
    }

    /**
     * item 底部圆角样式
     *
     * @param itemStr
     * @param itemId
     */
    private void itemBottom(String itemStr, int itemId) {
        itemDefault(itemStr, itemId);
        itemView.setBackgroundResource(R.drawable.bg_default_fillet_view_bottom);
    }

    /**
     * item 顶部圆角样式
     *
     * @param itemStr
     * @param itemId
     */
    private void itemTop(String itemStr, int itemId) {
        itemDefault(itemStr, itemId);
        itemView.setBackgroundResource(R.drawable.bg_default_fillet_view_top);
    }

    /**
     * item 顶部圆角样式
     *
     * @param itemStr
     * @param itemId
     */
    private void itemAll(String itemStr, int itemId) {
        itemDefault(itemStr, itemId);
        itemView.setBackgroundResource(R.drawable.bg_default_fillet_view_all);
    }

    /**
     * 关闭dialog
     */
    public void dismissListDialog() {

        if (alertDialog != null)
            alertDialog.dismiss();
    }

    /**
     * 隐藏dialog
     */
    public void hideListDialog() {
        if (alertDialog != null)
            alertDialog.hide();
    }

}
