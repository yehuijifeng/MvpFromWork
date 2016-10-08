package com.android.skin.base;

import android.support.v7.app.AppCompatActivity;

import com.android.skin.interfaces.ISkinListener;
import com.android.skin.manager.SkinManager;

/**
 * Created by Luhao on 2016/6/2.
 */
public abstract class BaseSkinActivity extends AppCompatActivity implements ISkinListener {


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        //获得当前activity的最外层view
////        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
////        //在activity创建的时候添加换肤的监听
////        //鸿洋大神写的是直接传入一个activity实例，我这里稍微的修改了一下，并不算是优化，只能说方便约束和统一管理
////        //我这里不再传入activity对象，而是传入实现了这个接口的activity对象和它的view
////        //目的就是为了当我们继承这个抽象类的时候，其他地方如果用到了ViewGroup的话，我们也不用再次获取了
////        SkinManager.getInstance().register(this, root);
//    }

    /**
     * 当程序要求app修改皮肤的时候就会调用这个方法
     * 该方法里面写实时的修改皮肤的方法
     */
    @Override
    public void onSkinChanged() {
        //因为在onCreateView方法中已经将参数声明了，所以这里直接调用
        //如果皮肤管理器向所有注册过的view发出通知，改变皮肤；则就会出发该方法
        //而该方法中写的就是单个的修改view的皮肤
        SkinManager.getInstance().apply(this);
    }

    /**
     * 当activity被finish的时候我们从换肤集合中移除这个activity，下一次换肤就不会对该activity产生作用了
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册，从皮肤注册集合中移除，以免造成内存泄漏
        SkinManager.getInstance().unregister(this);
    }
}
