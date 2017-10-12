package com.river.reading.module.news.model;

import com.river.reading.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2016/8/25.
 */
public interface INewsChannelModel<T> {
  Subscription requestNewsChannel(RequestCallBack<T> callBack, String appid, String timestap,
      String sign);
}
