package com.android.mvp.http.download;

/**
 * Created by Luhao on 2016/6/24.
 * 下载监听接口
 */
public interface OnProgressListener {
    /**
     * @param progress 已经下载或上传字节数
     * @param total    总字节数
     * @param bytesRead    下载速度
     */
    void onProgress(double progress, double total, long bytesRead);


}
