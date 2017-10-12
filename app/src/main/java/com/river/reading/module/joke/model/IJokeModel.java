package com.river.reading.module.joke.model;

import com.river.reading.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface IJokeModel<T>{
  Subscription requestJokeList(RequestCallBack<T> callBack,String type,String maxResult,String page, String appid,
      String timestamp,String showapi_sign);
}
