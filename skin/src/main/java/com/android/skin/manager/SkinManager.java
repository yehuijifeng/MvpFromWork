package com.android.skin.manager;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.android.skin.contacts.SkinContact;
import com.android.skin.interfaces.ISkinCallback;
import com.android.skin.interfaces.ISkinListener;
import com.android.skin.utils.SkinSharedPreferencesUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/9/22.
 * tag换肤管理器
 */
public class SkinManager {
    private Application mContext;//这里的上下文其实是appliaction的上下文，因为换肤全局有效
    private Resources mResources;//换肤的资源对象
    private ResourceManager mResourceManager;//换肤的资源管理器
    private SkinSharedPreferencesUtils mPrefUtils;//用户偏好设置
    private boolean usePlugin;//是否可以换肤标识
    private String mSuffix = "";//应用内换肤的标识后缀
    private String mCurPluginPath;//插件换肤的插件文件路径
    private String mCurPluginPkg;//插件的内置包名
    private Map<ISkinListener, List<SkinView>> mSkinViewMaps = new HashMap<>();//键值对指向哪一个view对应的它之下所有的需要更换的皮肤资源
    private List<ISkinListener> mActivities = new ArrayList<>();//保存整个app被标记的要被换肤的对象

    private SkinManager() {
    }

    private static class SingletonHolder {
        static SkinManager sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.sInstance;
    }

    public ResourceManager getResourceManager() {
        if (!usePlugin) {
            mResourceManager = new ResourceManager(mContext.getResources(), mContext.getPackageName(), mSuffix);
        }
        return mResourceManager;
    }


    /**
     * init是在appliaction中调用的方法，为app第一次进来的时候，验证用户是否使用了插件皮肤，如果使用了则加载皮肤。
     *
     * @param context
     */
    public void init(Application context) {
        mContext = context;
        mPrefUtils = new SkinSharedPreferencesUtils(mContext);
        String skinPluginPath = mPrefUtils.getPluginPath();
        String skinPluginPkg = mPrefUtils.getPluginPackage();
        mSuffix = mPrefUtils.getAttrSuffix();
        if (!validPluginParams(skinPluginPath, skinPluginPkg))
            return;
        try {
            loadPlugin(skinPluginPath, skinPluginPkg, mSuffix);
            mCurPluginPath = skinPluginPath;
            mCurPluginPkg = skinPluginPkg;
        } catch (Exception e) {
            mPrefUtils.clear();
            e.printStackTrace();
        }
    }

    private PackageInfo getPackageInfo(String skinPluginPath) {
        PackageManager pm = mContext.getPackageManager();
        return pm.getPackageArchiveInfo(skinPluginPath, PackageManager.GET_ACTIVITIES);
    }

    /**
     * 加载插件资源，建议在非ui线程工作
     */
    private void loadPlugin(String skinPath, String skinPkgName, String suffix) throws Exception {
        //获得系统资源管理类实例
        AssetManager assetManager = AssetManager.class.newInstance();
        //获得系统资源管理类下的添加资源路径方法
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        //调用该方法，并且传入皮肤所在的路径
        addAssetPath.invoke(assetManager, skinPath);
        //获得resources对象，现在获得的还是当前app内的
        Resources superRes = mContext.getResources();
        //获得一个资源管理，显示指标，获取配置
        //创建一个新的资源对象的一组现有的资产管理公司的资产。
        mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mResourceManager = new ResourceManager(mResources, skinPkgName, suffix);
        usePlugin = true;
    }

    /**
     * 检查插件参数是否正确
     */
    private boolean validPluginParams(String skinPath, String skinPkgName) {
        if (TextUtils.isEmpty(skinPath) || TextUtils.isEmpty(skinPkgName)) {
            return false;
        }

        File file = new File(skinPath);
        if (!file.exists())
            return false;

        PackageInfo info = getPackageInfo(skinPath);
        if (!info.packageName.equals(skinPkgName))
            return false;
        return true;
    }

    /**
     * 插件参数异常
     */
    private void checkPluginParamsThrow(String skinPath, String skinPkgName) {
        if (!validPluginParams(skinPath, skinPkgName)) {
            throw new IllegalArgumentException("skinPluginPath or skinPkgName not valid ! ");
        }
    }

    /**
     * 清楚所有插件
     */
    public void removeAnySkin() {
        clearPluginInfo();
        notifyChangedListeners();
    }

    /**
     * 是否可以替换皮肤
     */
    public boolean needChangeSkin() {
        return usePlugin || !TextUtils.isEmpty(mSuffix);
    }


    /**
     * 应用内换肤，传入资源区别的后缀
     */
    public void changeSkin(String suffix) {
        if (suffix.equals(mSuffix)) return;
        clearPluginInfo();
        mSuffix = suffix;
        mPrefUtils.setAttrSuffix(suffix);
        notifyChangedListeners();
    }

    /**
     * 清楚插件皮肤的信息
     */
    private void clearPluginInfo() {
        mCurPluginPath = null;
        mCurPluginPkg = null;
        usePlugin = false;
        mSuffix = null;
        mPrefUtils.clear();
    }

    /**
     * 更新插件皮肤的信息
     *
     * @param skinPluginPath
     * @param pkgName
     * @param suffix
     */
    private void updatePluginInfo(String skinPluginPath, String pkgName, String suffix) {
        mPrefUtils.setPluginPath(skinPluginPath);
        mPrefUtils.setPluginPackage(pkgName);
        mPrefUtils.setAttrSuffix(suffix);
        mCurPluginPkg = pkgName;
        mCurPluginPath = skinPluginPath;
        mSuffix = suffix;
    }


    /**
     * 更换插件皮肤，默认为""，即外部apk和app内部相同资源名
     *
     * @param skinPluginPath 皮肤插件绝对路径
     * @param skinPluginPkg  绝对路径下apk包下资源包名
     * @param callback       回调接口
     */
    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, ISkinCallback callback) {
        changeSkin(skinPluginPath, skinPluginPkg, "", callback);
    }


    /**
     * 根据suffix选择插件内某套皮肤，外部apk与app资源名相同但是后缀名不同的资源
     *
     * @param skinPluginPath 皮肤插件绝对路径
     * @param skinPluginPkg  绝对路径下apk包下资源包名
     * @param suffix         后缀
     * @param callback       回调接口
     */
    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, final String suffix, final ISkinCallback callback) {
        if (callback == null) return;
        callback.onStart();
        try {
            checkPluginParamsThrow(skinPluginPath, skinPluginPkg);
        } catch (IllegalArgumentException e) {
            callback.onError(new RuntimeException("checkPlugin occur error"));
            return;
        }

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    loadPlugin(skinPluginPath, skinPluginPkg, suffix);
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer res) {
                if (res == 0) {
                    callback.onError(new RuntimeException("loadPlugin occur error"));
                    return;
                }
                try {
                    updatePluginInfo(skinPluginPath, skinPluginPkg, suffix);
                    notifyChangedListeners();
                    callback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }
        }.execute();
    }

    /**
     * 传入一个继承了换肤接口的activity，并且返回这个activity中所有的可以换肤的SkinView对象，获得一个需要换肤的view
     * 该map里面存入的是整个app被记录的需要换肤的view，传入接口，返回对应的实现了接口的view的所有资源对象
     */
    public List<SkinView> getSkinViews(ISkinListener listener) {
        return mSkinViewMaps.get(listener);
    }

    /**
     * 将每个皮肤接口的所有view的资源都保存在map中，然后方便存取对iang
     */
    public void putSkinViews(ISkinListener listener, List<SkinView> skinViews) {
        mSkinViewMaps.put(listener, skinViews);
    }

    /**
     * 执行换肤
     */
    public void apply(ISkinListener listener) {
        //获得map中存入的该view的所有资源并进行替换
        List<SkinView> skinViews = getSkinViews(listener);
        //LogUtils.i("skinViews的长度：" + skinViews.size());
        if (skinViews == null) return;
        for (SkinView skinView : skinViews) {
            //遍历替换每一个资源
            skinView.apply();
        }
    }

    /**
     * 注册换肤
     */
    public void register(final ISkinListener listener, final View view) {
        mActivities.add(listener);
        //这里将注入皮肤的操作添加到队列中执行，要不然会获取不到
        view.post(new Runnable() {
            @Override
            public void run() {
                injectSkin(listener, view);
            }
        });
    }

    /**
     * 反注册
     */
    public void unregister(ISkinListener listener) {
        mActivities.remove(listener);
        mSkinViewMaps.remove(listener);
    }

    /**
     * 通知所有注册过的view进行换肤
     */
    public void notifyChangedListeners() {
        for (ISkinListener listener : mActivities) {
            apply(listener);
        }
    }

    /**
     * 注入皮肤,在activity初始化的时候进行
     */
    public void injectSkin(ISkinListener iSkinListener, View view) {
        List<SkinView> skinViews = new ArrayList<>();
        addSkinViews(view, skinViews);
        putSkinViews(iSkinListener, skinViews);
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    /**
     * 递归添加view的资源到skinview结合中
     */
    public void addSkinViews(View view, List<SkinView> skinViews) {
        SkinView skinView = getSkinView(view);
        if (skinView != null) skinViews.add(skinView);
        if (view instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) view;
            int n = container.getChildCount();
            for (int i = 0; i < n; i++) {
                View child = container.getChildAt(i);
                addSkinViews(child, skinViews);
            }
        }

    }

    /**
     * 获得该view下所有的资源属性
     */
    public SkinView getSkinView(View view) {
        Object tag = view.getTag(SkinContact.SKIN_TAG);
        if (tag == null)
            tag = view.getTag();
        if (tag == null) return null;
        if (!(tag instanceof String)) return null;
        List<SkinAttr> skinAttrs = SkinAttrSupprot.getSkinTags(tag.toString());
        if (!skinAttrs.isEmpty()) {
            changeViewTag(view);
            return new SkinView(view, skinAttrs);
        }
        return null;
    }

    /**
     * 修改view所持有的tag标签
     */
    private void changeViewTag(View view) {
        Object tag = view.getTag(SkinContact.SKIN_TAG);
        if (tag == null) {
            tag = view.getTag();
            view.setTag(SkinContact.SKIN_TAG, tag);
            //view.setTag(null);//我不知道为什么鸿洋大神最后要在添加了tag后又置空，我把它注释了，不影响运行。
        }
    }

}
