package com.river.reading.module.joke.model;

import com.river.reading.bean.JokeBean;
import com.river.reading.callback.RequestCallBack;
import com.river.reading.http.HostType;
import com.river.reading.http.RetrofitManager;
import com.socks.library.KLog;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/11/28.
 */

public class IJokeModelImpl implements IJokeModel<JokeBean> {
  @Override
  public Subscription requestJokeList(RequestCallBack<JokeBean> callBack,String type, String maxResult, String page,
      String appid, String timestamp,String showapi_sign) {
    return RetrofitManager.getInstance(HostType.NEWS_HOST).getTextJokeList(type,maxResult,page,appid,timestamp,showapi_sign)
        .subscribe(new Subscriber<JokeBean>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            callBack.requestError(e.getMessage());
            KLog.d("TAG",e.fillInStackTrace());
          }

          @Override public void onNext(JokeBean jokeBean) {
            callBack.requestSuccess(jokeBean);
          }
        });
  }
}
