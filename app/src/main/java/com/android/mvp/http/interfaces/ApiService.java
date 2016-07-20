package com.android.mvp.http.interfaces;


import com.android.mvp.http.request.RequestUrls;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by 浩 on 2016/6/22.
 * retrofit服务接口
 * 每一个函数都必须有提供请求方式和相对URL的Http注解，
 * Retrofit提供了5种内置的注解：GET、POST、PUT、DELETE和HEAD，在注解中指定的资源的相对URL
 */
public interface ApiService {
    /**
     * 注解的使用方法：
     * 在外面，你将会为你的请求设置一个url
     * 所以，无论哪一种注解都是拼接在url后面的
     */

    //实际上在get的开始已经有一个rul存在了
    //例如，我们的url是“http://gc.ditu.aliyun.com/”
    //那么get注解前就已经存在了这个url，并且使用{}替换符得到的最终url是：
    // http://gc.ditu.aliyun.com/geocoding?a=苏州市
    //那么key中的值也就是从注解path中得到的，它并要求和{}替换符中的字符一致
    //参数不能为null,且不能只有url的参数，还应该包括地址的字段；正确：geocoding?a=苏州市；错误：a=苏州市
//    @GET(RequestUrls.GET_INDEX_URL)
//    Observable<HttpBean<IndexBean>> getIndex();

//    @POST(RequestUrls.GET_GOODS_LIST_URL)
//    Observable<HttpBean<GoodsListBean>> getGoodsList(@QueryMap Map<String, Object> options);

    @GET(RequestUrls.GET_DOWN_FILE)
    Call<ResponseBody> getDownFile();

    @Multipart
    @POST(RequestUrls.GET_UPLOAD_FILE)
    Call<ResponseBody> getUploadFile(
            @PartMap Map<String, RequestBody> params,
            @QueryMap Map<String, Object> options);

}
