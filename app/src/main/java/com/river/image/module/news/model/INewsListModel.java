package com.river.image.module.news.model;

import com.river.image.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/25.
 */

public interface INewsListModel<T> {
  Subscription requestNewsList(RequestCallBack<T> callBack,String channelId, String channelName, String maxResult,
      String needAllList, String needContent, String needHtml, String page, String appid,
      String timestamp, String title,String showapi_sign);
}
