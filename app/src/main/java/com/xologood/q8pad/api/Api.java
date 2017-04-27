package com.xologood.q8pad.api;


import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xologood.mvpframework.App;
import com.xologood.mvpframework.util.NetWorkUtil;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.utils.AppUtils;
import com.xologood.q8pad.utils.Keybase;
import com.xologood.q8pad.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * des:retorfit api
 * Created by xsf
 * on 2016.06.15:47
 */
public class Api {
    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 20000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 20000;
    private static final String TAG = "Api";
    public Retrofit retrofit;
    public ApiService apiService;

    private static SparseArray<Api> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);

    /*************************缓存设置*********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    private static String mHost;

    /**
     * =============================================================================================
     */
    //构造方法私有
    private Api(int hostType,Interceptor interceptor) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(Qpadapplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                //  .addInterceptor(mInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(interceptor)
                //    .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        mHost = ApiConstants.getHost(hostType);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mHost)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * =============================================================================================
     * @param hostType
     */

    //构造方法私有
    private Api(int hostType) {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(Qpadapplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
      /*  //增加头部信息
        Interceptor headerInterceptor =new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };*/

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                //  .addInterceptor(mRewriteCacheControlInterceptor)
                //  .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                // .addInterceptor(headerInterceptor)
                .addInterceptor(commoninterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        mHost = ApiConstants.getHost(hostType);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mHost)
                .build()    ;
        apiService = retrofit.create(ApiService.class);
    }



    //获取登录后ApiService单例
    public static ApiService getLoginInInstance(int hostType, final String recorderBase, final String  sysKeyBase) {
        Api retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new Api(hostType, new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request oldRequest = chain.request();
                    //   Log.i(TAG, "intercept:旧host："+oldRequest.url().host()+"旧scheme："+oldRequest.url().scheme()+"旧url"+oldRequest.url());
                    // 添加新的参数
                    HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                            .newBuilder()
                            .scheme(oldRequest.url().scheme())
                            .host(oldRequest.url().host())
                            .addQueryParameter("keyBase", Keybase.getKeyBase())
                            .addQueryParameter("versionBase", AppUtils.getVersionName(Qpadapplication.getAppContext()))
                            .addQueryParameter("projectNameBase", "千里码Q8云战略合作平台-APP")
                            .addQueryParameter("recorderBase", recorderBase)
                            .addQueryParameter("sysKeyBase", sysKeyBase);
                    // 新的请求
                    Request newRequest = oldRequest.newBuilder()
                            .method(oldRequest.method(), oldRequest.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    Log.i(TAG, "intercept: 新url:  " + newRequest.url());
                    return chain.proceed(newRequest);
                }
            });
            sRetrofitManager.put(hostType, retrofitManager);
        } else if (!ApiConstants.getHost(hostType).equals(mHost)) {
            retrofitManager = new Api(hostType, new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request oldRequest = chain.request();
                    //   Log.i(TAG, "intercept:旧host："+oldRequest.url().host()+"旧scheme："+oldRequest.url().scheme()+"旧url"+oldRequest.url());
                    // 添加新的参数
                    HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                            .newBuilder()
                            .scheme(oldRequest.url().scheme())
                            .host(oldRequest.url().host())
                            .addQueryParameter("keyBase", Keybase.getKeyBase())
                            .addQueryParameter("versionBase", AppUtils.getVersionName(Qpadapplication.getAppContext()))
                            .addQueryParameter("projectNameBase", "千里码Q8云战略合作平台-APP")
                            .addQueryParameter("recorderBase", recorderBase)
                            .addQueryParameter("sysKeyBase", sysKeyBase);
                    // 新的请求
                    Request newRequest = oldRequest.newBuilder()
                            .method(oldRequest.method(), oldRequest.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    Log.i(TAG, "intercept: 新url:  " + newRequest.url());
                    return chain.proceed(newRequest);
                }
            });
            sRetrofitManager.put(hostType, retrofitManager);
        }
        return retrofitManager.apiService;
    }


    /**
     * 获取登录时ApiService单例
     * @param hostType
     */
    public static ApiService getDefault(int hostType) {
        Api retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new Api(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        } else if (!ApiConstants.getHost(hostType).equals(mHost)) {
            retrofitManager = new Api(hostType);
            sRetrofitManager.put(hostType,retrofitManager);
        }
        return retrofitManager.apiService;
    }



    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(Qpadapplication.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    private final   Interceptor commoninterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                Log.i(TAG, "intercept:旧host："+oldRequest.url().host()+"旧scheme："+oldRequest.url().scheme()+"旧url"+oldRequest.url());
                // 添加新的参数
                HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter("keyBase", Keybase.getKeyBase())
                        .addQueryParameter("recorderBase", "1")
                        .addQueryParameter("versionBase", AppUtils.getVersionName(Qpadapplication.getAppContext()))
                        .addQueryParameter("projectNameBase", "千里码Q8云战略合作平台-APP")
                        .addQueryParameter("sysKeyBase", "系统登陆");
                // 新的请求
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(authorizedUrlBuilder.build())
                        .build();
                Log.i(TAG, "intercept: 新url:"+newRequest.url());
                return chain.proceed(newRequest);
            }
        };
    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetConnected(Qpadapplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(Qpadapplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(App.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}