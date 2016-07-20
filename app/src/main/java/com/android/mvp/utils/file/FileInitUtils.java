package com.android.mvp.utils.file;

import android.os.Environment;

import com.android.mvp.constances.AppConstant;

import java.io.File;

/**
 * Created by Luhao on 2016/6/22.
 */
public class FileInitUtils {
    /**
     * 创建sd卡下存放照片的文件夹
     */
    public void createSaveImage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String path = AppConstant.SAVE_IMG_PATH;
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
        }
    }

    /**
     * 创建sd卡下存放缓存图片的文件夹
     */
    public void createCacheImage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String path = AppConstant.CACHE_IMG_PATH;
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
        }
    }

    /**
     * 创建sd卡下存放日志的文件夹
     */
    public void createLog() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String path = AppConstant.LOG_PATH;
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
        }
    }

    /**
     * 创建sd卡下存放配置文件的文件夹
     */
    public void createSettigns() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String path = AppConstant.SETTINGS_PATH;
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
        }
    }

    /**
     * 创建sd卡下存放配置文件的文件夹
     */
    public void createFiles() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String path = AppConstant.FILES_PATH;
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
        }
    }
}
