package com.android.mvp.utils.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.android.mvp.constances.AppConstant;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Luhao on 2016/6/22.
 * 本机文件操作工具类
 */
public class FileUtils {
    /**
     * 判断SDCard是否可用
     */
    public boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 是否存在该文件夹
     */
    public boolean isHaveFileDirectory(String path) {

        File file = new File(path);
        if (file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否存在该文件
     */
    public boolean isHaveFile(String path) {

        File file = new File(path);
        if (file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * sd卡根目录路径
     */
    public File getSDFile() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获得SD卡总大小
     */
    public String getSDAllSize(Context con) {
        File path = getSDFile();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(con, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    public String getSDRemainingSize(Context con) {
        File path = getSDFile();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(con, blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     */
    public String getRomAllSize(Context con) {
        File path = getSDFile();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(con, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     */
    public String getRomRemainingSize(Context con) {
        File path = getSDFile();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(con, blockSize * availableBlocks);
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(AppConstant.getSDPath())) {
            filePath = AppConstant.getSDPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }


    /**
     * 添加文件到指定路径,该文件创建出来是空的
     *
     * @return
     */
    public boolean createFile(String filePath, String fileContent) {
        if (TextUtils.isEmpty(fileContent) || TextUtils.isEmpty(filePath)) return false;
        try {
            File file1 = new File(filePath);
            if (!file1.exists()) {
                file1.createNewFile();
            } else {
                file1.delete();
                file1.createNewFile();
            }
            FileOutputStream fout1 = new FileOutputStream(filePath);
            byte[] bytes1 = fileContent.getBytes();
            fout1.write(bytes1);
            fout1.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //读SD中的文件
    public String readZhongduanFile(String filePath) {
        String res = "";
        try {
            File file1 = new File(filePath);
            if (!file1.exists()) return null;
            FileInputStream fin1 = new FileInputStream(filePath);
            int length1 = fin1.available();
            byte[] buffer1 = new byte[length1];
            fin1.read(buffer1);
            res = EncodingUtils.getString(buffer1, "UTF-8");
            fin1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
