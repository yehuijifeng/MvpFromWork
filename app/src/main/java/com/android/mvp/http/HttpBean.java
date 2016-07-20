package com.android.mvp.http;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Luhao on 2016/6/23.
 * http请求返回数据
 * 所有的请求实体类都要继承该请求实体类
 */
public class HttpBean<T> implements Parcelable {
    /**
     * statusCode : 200
     * message : 查询成功！
     */
    private int statusCode;//状态码
    private String message;//返回值描述
    private T object;//一个对象
    private List<T> objects;//对象集合

    public HttpBean() {
    }

    public HttpBean(int statusCode) {
        this.statusCode = statusCode;
    }

    protected HttpBean(Parcel in) {
        statusCode = in.readInt();
        message = in.readString();
    }

    public static final Creator<HttpBean> CREATOR = new Creator<HttpBean>() {
        @Override
        public HttpBean createFromParcel(Parcel in) {
            return new HttpBean(in);
        }

        @Override
        public HttpBean[] newArray(int size) {
            return new HttpBean[size];
        }
    };

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeString(message);
    }
}
