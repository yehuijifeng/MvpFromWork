package com.android.mvp.http.download;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Luhao on 2016/6/24.
 * 对于文件的下载我们需要重写ResponseBody类中的一些方法
 * 我们计算已经读取文件的字节数，并且调用了ProgressListener接口。
 * 所以这个ProgressListener接口是在子线程中运行的。
 */
public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;//请求体
    private final OnProgressListener progressListener;//下载进度监听接口
    private BufferedSource bufferedSource;//缓冲流

    public ProgressResponseBody(ResponseBody responseBody, OnProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }


    /**
     * 媒体类型
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 内容长度
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 缓冲源
     *
     * @return
     * @throws IOException
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null)
            bufferedSource = Okio.buffer(source(responseBody.source()));
        return bufferedSource;
    }

    private long timeout;

    /**
     * 返回源
     *
     * @param source
     * @return
     */
    private Source source(Source source) {
        //转发来源
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (System.currentTimeMillis() - timeout > 500||bytesRead==-1) {//如果下载的内容
                    progressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead);
                    timeout = System.currentTimeMillis();
                }
                return bytesRead;
            }
        };
    }
}
