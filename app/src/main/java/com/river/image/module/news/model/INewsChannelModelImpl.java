package com.river.image.module.news.model;

import android.util.Log;
import com.river.image.bean.NewsChannel;
import com.river.image.callback.RequestCallBack;
import com.river.image.db.NewsChannelDB;
import com.river.image.http.HostType;
import com.river.image.http.RetrofitManager;
import com.socks.library.KLog;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/8/29.
 */
public class INewsChannelModelImpl implements INewsChannelModel<NewsChannel> {
  @Override
  public Subscription requestNewsChannel(final RequestCallBack<NewsChannel> callBack, String appid,
      String timestap, String sign) {
    return RetrofitManager.getInstance(HostType.NEWS_HOST).getNewsChannel(appid,timestap,sign).
        subscribe(new Subscriber<NewsChannel>() {
          @Override public void onCompleted() {
            Log.d("TAG","onCompleted--->INewsChannelModelImpl");
          }
          @Override public void onError(Throwable e) {
            e.printStackTrace();
            callBack.requestError(e.getMessage());
            KLog.d("TAG",e.fillInStackTrace());
          }
          @Override public void onNext(NewsChannel newsChannels) {
            KLog.d("TAG","INewsChannelModelImpl");
           callBack.requestSuccess(newsChannels);
            NewsChannelDB.insertDB(newsChannels);

          }
        });
  }
}
