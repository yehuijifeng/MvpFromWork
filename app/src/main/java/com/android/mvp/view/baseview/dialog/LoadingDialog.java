package com.android.mvp.view.baseview.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mvp.R;


/**
 * Created by yehuijifeng
 * on 2016/1/7.
 * loading页
 */
public class LoadingDialog extends View {

    private View root;
    private ImageView loading_img;
    private TextView loading_text;
    private ProgressDialog progressDialog;
    private Animation animation;
    public LoadingDialog(Context context) {
        super(context);
    }
    private LinearInterpolator linearInterpolator;//线性插值器，根据时间百分比设置属性百分比
    /**
     * 关闭dialog
     */
    public void dismissLoadingDialog() {
        if(loading_img!=null)
        loading_img.clearAnimation();
        if(progressDialog!=null)
        progressDialog.dismiss();
    }

    private void initView(){
        root =  View.inflate(getContext(), R.layout.dialog_loading, null);
        loading_text = (TextView) root.findViewById(R.id.loading_text);
        loading_img = (ImageView) root.findViewById(R.id.loading_img);

        animation= new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);//子心旋转
        linearInterpolator= new LinearInterpolator();
        //setInterpolator表示设置旋转速率。
        animation.setInterpolator(linearInterpolator);
        animation.setRepeatCount(-1);//-1表示循环运行
        animation.setDuration(1000);

        progressDialog=new ProgressDialog(getContext(),R.style.dialog);
        progressDialog.setCanceledOnTouchOutside(false);

    }
    public void showLoadingDialog(String loadingStr, Drawable drawable) {
        initView();
        if (!TextUtils.isEmpty(loadingStr))
            loading_text.setText(loadingStr + "");
        if (drawable != null)
            loading_img.setImageDrawable(drawable);
        progressDialog.show();
        progressDialog.setContentView(root);
        loading_img.startAnimation(animation);
    }

    public void showLoadingDialog(String loadingStr) {
        showLoadingDialog(loadingStr, null);
    }

    public void showLoadingDialog() {
        showLoadingDialog(null, null);
    }
}
