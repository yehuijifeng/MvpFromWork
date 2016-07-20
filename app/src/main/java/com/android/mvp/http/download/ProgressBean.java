package com.android.mvp.http.download;

/**
 * Created by Luhao on 2016/6/24.
 */
public class ProgressBean {
    private double progress;//已下载数
    private double contentLength;//总长度
    private long bytesRead;//下载速度，-1为下载完成

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public double getContentLength() {
        return contentLength;
    }

    public void setContentLength(double contentLength) {
        this.contentLength = contentLength;
    }

}
