package com.android.mvp.view.baseview.dialog;

import android.app.ProgressDialog;
import android.content.Context;
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
    private ListOnClickListener listOnClickListener;
    private LinearLayout list_exit_layout;
    private LinearLayout.LayoutParams layoutParams;
    View itemView;
    TextView textView;

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
        alertDialog = new ProgressDialog(getContext(), R.style.dialog);
    }

    /**
     * 确定和返回键的回调接口
     */
    public interface ListOnClickListener {
        void onCancel();

        void onItems(int item, String itemName);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.list_exit_layout) {
            dismissListDialog();
            listOnClickListener.onCancel();
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
            listOnClickListener.onItems(item, itemName);
        }
    }

    public void showListDialog(String[] itemStr, ListOnClickListener listOnClickListener) {
        this.listOnClickListener = listOnClickListener;
        initView();
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = DisplayUtils.dip2px(getContext(), 0.5f);
        for (int i = 0; i < itemStr.length; i++) {
            itemView = View.inflate(getContext(), R.layout.dialog_list_item, null);
            textView = (TextView) itemView.findViewById(R.id.list_item_text);
            textView.setText(itemStr[i]);
            itemView.setLayoutParams(layoutParams);
            if (itemStr.length > 1 && i == 0) {
                itemView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_default_fillet_view_top));
            } else if (itemStr.length > 1 && i == itemStr.length-1) {
                itemView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_default_fillet_view_bottom));
            }
            list_layout.addView(itemView);
            itemView.setOnClickListener(new onItemClick(i, itemStr[i]));
        }
        alertDialog.show();
        alertDialog.setContentView(root);
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
