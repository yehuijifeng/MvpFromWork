package com.android.mvp.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.android.mvp.appliaction.ActivityCollector;
import com.android.mvp.appliaction.MvpAppliaction;
import com.android.mvp.bean.app.AppInfoBean;
import com.android.mvp.constances.AppConstant;

import java.util.List;

/**
 * Created by yehuijifeng
 * on 2016/1/5.
 * 获取app自身信息的工具类
 */
public class AppUtils {
    /**
     * 防止被实例化
     */
    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = MvpAppliaction.getInstance().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(MvpAppliaction.getInstance().getPackageName(), 0);
            return MvpAppliaction.getInstance().getResources().getString(packageInfo.applicationInfo.labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序包名
     */
    public static String getPackageName() {
        String packageNames = null;
        try {
            PackageInfo info = MvpAppliaction.getInstance().getPackageManager().getPackageInfo(MvpAppliaction.getInstance().getPackageName(), 0);
            // 当前应用的版本名称
            String versionName = info.versionName;
            // 当前版本的版本号
            int versionCode = info.versionCode;
            // 当前版本的包名
            packageNames = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageNames;
    }

    /**
     * 获取应用程序版本名称
     */
    public static String getVersionName() {
        String versionName = null;
        try {
            PackageInfo info = MvpAppliaction.getInstance().getPackageManager().getPackageInfo(MvpAppliaction.getInstance().getPackageName(), 0);
            // 当前应用的版本名称
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取应用程序版本号
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            PackageInfo info = MvpAppliaction.getInstance().getPackageManager().getPackageInfo(MvpAppliaction.getInstance().getPackageName(), 0);
            // 当前版本的版本号
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取程序 图标
     */
    public static Drawable getAppIcon() {
        try {
            PackageManager pm = MvpAppliaction.getInstance().getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(getPackageName(), 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得整个app信息
     */
    public static AppInfoBean getAppInfo() {
        AppInfoBean appInfoBean = null;
        try {
            appInfoBean = new AppInfoBean();
            PackageManager packageManager = MvpAppliaction.getInstance().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(MvpAppliaction.getInstance().getPackageName(), 0);
            // 当前应用名称
            appInfoBean.setAppName(MvpAppliaction.getInstance().getResources().getString(packageInfo.applicationInfo.labelRes));
            PackageInfo info = MvpAppliaction.getInstance().getPackageManager().getPackageInfo(MvpAppliaction.getInstance().getPackageName(), 0);
            // 当前版本的版本号
            appInfoBean.setVersionCode(info.versionCode);
            // 当前应用的版本名称
            appInfoBean.setVersionName(info.versionName);
            // 当前版本的包名
            appInfoBean.setPackageName(info.packageName);
            PackageManager pm = MvpAppliaction.getInstance().getPackageManager();
            ApplicationInfo infos = pm.getApplicationInfo(getPackageName(), 0);
            // 当前图标
            appInfoBean.setIcon(infos.loadIcon(pm));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfoBean;
    }

    /**
     * 判断是否第一次进入应用
     *
     * @param context
     * @return
     */
    public static boolean isOneStart(Context context) {
        SharedPreferencesUtils sharedPreferencesUtil = new SharedPreferencesUtils(context);
        Boolean isOneStart = sharedPreferencesUtil.getBoolean(AppConstant.IS_ONE_START, true);//获取这个值，如果没有这个值则去第二个参数，即取默认值
        if (isOneStart) {//第一次
            sharedPreferencesUtil.saveBoolean(AppConstant.IS_ONE_START, false);
            return true;
        }
        return false;
    }

    /**
     * 判断该activity是否处于栈顶
     * android 5.0以后弃用，有时候判断不准确，慎用！
     * @param activty
     * @return
     */
    public static boolean isTopActivity(Activity activty) {
        ActivityManager activityManager = (ActivityManager) activty.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = activityManager.getRunningTasks(1);
        if (runningTaskInfoList != null) {
            String topActivity = runningTaskInfoList.get(0).topActivity.toString();
            return topActivity.equals(activty.getComponentName().toString());
        } else
            return false;
    }


    /**
     * 重启app
     */
    public static void reStartApp(final Context context, Class cla) {
        try {
            Intent intent = new Intent(context, cla);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent restartIntent = PendingIntent.getActivity(
                    context, 0, intent, 0);
            //退出程序
            AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
            Thread.sleep(1000);
            ActivityCollector.finishAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
