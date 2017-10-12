package com.river.reading.http;

import com.river.reading.common.MyApplication;
import com.river.reading.bean.GirlsBean;
import com.river.reading.bean.JokeBean;
import com.river.reading.bean.NewsBean;
import com.river.reading.bean.NewsChannel;
import com.river.reading.utils.NetUtil;
import com.socks.library.KLog;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/9.
 */
public class RetrofitManager {
  private static PreService mService;
  private volatile static OkHttpClient mOkHttpClient;
  //设缓存有效期为两天
  private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
  //相当于HashMap
 // private static SparseArray<RetrofitManager> mRMInstance = new SparseArray<>(HostType.TYPE_COUNT);
  private static RetrofitManager instance;

  //获取根据hostType来获取该类型的RetrofitManager单例
  public static RetrofitManager getInstance(int hostType) {
    instance = new RetrofitManager(hostType);
    return instance;
  }

  private RetrofitManager(@HostType.HostTypeChecker int hostType) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConfig.getHost(hostType))
        .client(getOkHttpClient())
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    mService = retrofit.create(PreService.class);
  }

  private OkHttpClient getOkHttpClient() {
    if (mOkHttpClient == null) {
      synchronized (RetrofitManager.class) {
        if (mOkHttpClient == null) {
          mOkHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
              .connectTimeout(30, TimeUnit.SECONDS)
              .writeTimeout(3, TimeUnit.SECONDS)
              .readTimeout(3, TimeUnit.SECONDS)
              .addNetworkInterceptor(mRewriteCacheControlInterceptor)
              .addInterceptor(mLoggingInterceptor)
              .build();
        }
      }
    }
    return mOkHttpClient;
  }

  // 云端响应头拦截器，用来配置缓存策略
  private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
    @Override public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();

      KLog.e("请求网址: " + request.url());

      if (!NetUtil.isConnected(MyApplication.getContext())) {
        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        KLog.e("没有网络");
      }
      Response originalResponse = chain.proceed(request);

      if (NetUtil.isConnected(MyApplication.getContext())) {
        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
        String cacheControl = request.cacheControl().toString();
        return originalResponse.newBuilder()
            .header("Cache-Control", cacheControl)
            .removeHeader("Pragma")
            .build();
      } else {
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
            .removeHeader("Pragma")
            .build();
      }
    }
  };

  // 打印返回的json数据拦截器
  private Interceptor mLoggingInterceptor = chain -> {

    final Request request = chain.request();
    final Response response = chain.proceed(request);

    final ResponseBody responseBody = response.body();
    final long contentLength = responseBody.contentLength();

    BufferedSource source = responseBody.source();
    source.request(Long.MAX_VALUE); // Buffer the entire body.
    Buffer buffer = source.buffer();

    Charset charset = Charset.forName("UTF-8");
    MediaType contentType = responseBody.contentType();
    if (contentType != null) {
      try {
        charset = contentType.charset(charset);
      } catch (UnsupportedCharsetException e) {
        KLog.e("");
        KLog.e("Couldn't decode the response body; charset is likely malformed.");
        return response;
      }
    }

    if (contentLength != 0) {
      KLog.v(
          "--------------------------------------------开始打印返回数据----------------------------------------------------");
      KLog.json(buffer.clone().readString(charset));
      KLog.v(
          "--------------------------------------------结束打印返回数据----------------------------------------------------");
    }

    return response;
  };

  /**
   * 图片
   */
  public Observable<GirlsBean> getImageInfoObservable(String type, int count, int page) {
    return mService.getImageInfo(type, count, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  /**
   * 新闻频道
   */
  public Observable<NewsChannel> getNewsChannel(String appId, String timesamp, String sign) {
    return mService.getNewsChannel(appId, timesamp, sign)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  /**
   * 新闻列表
   */
  public Observable<NewsBean> getNewsList(String channelId, String channelName, String maxResult,
      String needAllList, String needContent, String needHtml, String page, String appid,
      String timestamp, String title, String showapi_sign) {
    return mService.getNewsList(channelId, channelName, maxResult, needAllList, needContent,
        needHtml, page, appid, timestamp, title, showapi_sign)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  /**
   * 笑话
   */
  public Observable<JokeBean> getTextJokeList(String type, String maxResult, String page,
      String appid, String timestamp, String showapi_sign) {
    return mService.getTextJokeList(type, maxResult, page, appid, timestamp, showapi_sign)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }
}
