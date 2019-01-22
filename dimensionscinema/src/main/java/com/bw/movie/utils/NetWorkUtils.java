package com.bw.movie.utils;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 工具类
 */
public class NetWorkUtils {

    private Retrofit retrofit;
    private OkHttpClient client;
    //单例模式
    private static NetWorkUtils minstance;
    public static NetWorkUtils getMinstance() {
        if (minstance == null){
            minstance = new NetWorkUtils();
        }
        return minstance;
    }
    //私有构造方法
    private NetWorkUtils() {
        init();
    }

    public void init(){
        //初始化okhttp和设置日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        //初始化retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://172.17.8.100/")//设置url
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //返回okhttp
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
