package com.android.mvp.appliaction;

import android.app.Application;
import android.content.Context;

import com.android.mvp.constances.AppConstant;
import com.android.mvp.function.imageloader.ImageOptions;
import com.android.mvp.utils.LanguageUtils;
import com.android.mvp.utils.file.FileInitUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Luhao on 2016/6/22.
 * appliaction
 */
public class MvpAppliaction extends Application {

    /**
     * 将appliaction设置成单例
     */

    public static MvpAppliaction getInstance() {
        return MvpAppliactions.mvpAppliaction;
    }

    private static class MvpAppliactions {
        private static final MvpAppliaction mvpAppliaction = new MvpAppliaction();
    }

    //取得默认image的配置类
    public DisplayImageOptions defaultOptions = ImageOptions.defaultOptions();

    //取得圆形image的配置类
    public DisplayImageOptions roundOptions = ImageOptions.roundOptions();

    @Override
    public void onCreate() {
        super.onCreate();

        Observable
                .create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {

                        /**
                         *创建本地项目文件夹
                         */
                        FileInitUtils fileInitUtils = new FileInitUtils();
                        fileInitUtils.createSaveImage();
                        fileInitUtils.createFiles();
                        fileInitUtils.createLog();
                        fileInitUtils.createCacheImage();
                        fileInitUtils.createSettigns();
                        /**
                         * 初始化imageloader
                         */
                        initImageLoader(MvpAppliaction.this);
                    }
                })
                .subscribeOn(Schedulers.newThread())//使用subscribeOn()指定观察者代码运行的线程；它把以上的代码放在的非ui线程
                .observeOn(AndroidSchedulers.mainThread())//使结果在android主线程运行
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        /**
                         * 全局捕获异常的代理类
                         */
                        CrashHandler.getInstance().init(getApplicationContext());
                        /**
                         * 适配语言
                         * */
                        LanguageUtils languageUtils = new LanguageUtils(MvpAppliaction.this);
                        languageUtils.adapterLanguage();
                    }
                });


    }

    /**
     * 加载图片配置信息
     */
    private void initImageLoader(Context context) {
        /**
         * 1.完成ImageLoaderConfiguration的配置
         */
        //默认配置
        //ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        //自定义配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(720, 1280)//额外的内存缓存选项, 即保存的每个缓存文件的最大长宽
                .threadPoolSize(5)//线程池的并发数大小
                .threadPriority(Thread.NORM_PRIORITY - 1)//线程池的优先级，标准优先级
                .tasksProcessingOrder(QueueProcessingType.LIFO)//任务处理订单，队列处理类型.FIFO:先进先出 LIFO:先进后出
                .denyCacheImageMultipleSizesInMemory()//缓存显示不同大小的同一张图片
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))//最低内存缓存
                .memoryCacheSize(5 * 1024 * 1024)//内存缓存大小，5M
                .memoryCacheSizePercentage(20)//内存缓存百分比
                .diskCacheFileCount(100) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(new File(AppConstant.CACHE_IMG_PATH)))//自定义缓存图片地址
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .imageDownloader(new BaseImageDownloader(context)) //图片下载，当前页面
                .imageDecoder(new BaseImageDecoder(true)) //图片解码
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) //默认图像显示选项：创建简单的
                .writeDebugLogs()//写入debug的log
                .build();

        /**
         *2.单例ImageLoader类的初始化
         */
        ImageLoader.getInstance().init(config);

    }
}
