package com.river.image.http;

import android.util.SparseArray;
import com.river.image.bean.GirlsBean;
import com.river.image.bean.NewsBean;
import com.river.image.bean.NewsChannel;
import com.river.image.bean.TextJokeBean;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
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

  //相当于HashMap
  private static SparseArray<RetrofitManager> mRMInstance = new SparseArray<>();

  //获取根据hostType来获取该类型的RetrofitManager单例
  public static RetrofitManager getInstance(int hostType) {
   // KLog.d("TAG","hostType"+hostType);
    RetrofitManager instance = mRMInstance.get(hostType);
    if (instance == null) {
      instance = new RetrofitManager(hostType);
      mRMInstance.put(hostType, instance);
    //  KLog.d("TAG","RetrofitManager1");
      return instance;

    }
   // KLog.d("TAG","RetrofitManager2");
    return instance;
  }

  private RetrofitManager(int hostType) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConfig.getHost(hostType))
        .client(getOkHttpClient())
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    mService = retrofit.create(PreService.class);
  }

  private static OkHttpClient getOkHttpClient() {
    if (mOkHttpClient == null) {
      synchronized (RetrofitManager.class) {
        if (mOkHttpClient == null) {
          mOkHttpClient = new OkHttpClient.Builder()
             .retryOnConnectionFailure(true)
              .connectTimeout(30, TimeUnit.SECONDS)
             .writeTimeout(3, TimeUnit.SECONDS)
             .readTimeout(3, TimeUnit.SECONDS)
              .build();
        }
      }
    }
    return mOkHttpClient;
  }

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
   * @param channelId
   * @param channelName
   * @param maxResult
   * @param needAllList
   * @param needContent
   * @param needHtml
   * @param page
   * @param appid
   * @param timestamp
   * @param title
   * @return
   */
  public Observable<NewsBean> getNewsList(String channelId, String channelName, String maxResult,
      String needAllList, String needContent, String needHtml, String page, String appid,
      String timestamp, String title,String showapi_sign) {
    return mService.getNewsList(channelId, channelName, maxResult, needAllList, needContent,
        needHtml, page, appid, timestamp, title,showapi_sign)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }
  /**
   * 文本笑话
   */
  public Observable<TextJokeBean>  getTextJokeList(String type,String maxResult,String page,String appid,
      String timestamp,String showapi_sign){
    return mService.getTextJokeList(type,maxResult,page,appid,timestamp,showapi_sign)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }
}
