package com.river.image.module.news.model;

import com.river.image.bean.NewsBean;
import com.river.image.callback.RequestCallBack;
import com.river.image.http.HostType;
import com.river.image.http.RetrofitManager;
import com.socks.library.KLog;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/25.
 */

public class INewsListModelImpl implements INewsListModel<NewsBean> {
  @Override public Subscription requestNewsList(final RequestCallBack<NewsBean> callBack, String channelId,
      String channelName, String maxResult, String needAllList, String needContent, String needHtml,
      String page, String appid, String timestamp, String title,String showapi_sign) {
    return RetrofitManager.getInstance(HostType.NEWS_HOST).getNewsList(channelId, channelName, maxResult, needAllList, needContent,
        needHtml, page, appid, timestamp, title,showapi_sign)
        .subscribe(new Subscriber<NewsBean>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        callBack.requestError(e.getMessage());
        KLog.d("TAG",e.fillInStackTrace());
      }
      @Override public void onNext(NewsBean newsBean) {
        callBack.requestSuccess(newsBean);
      }
    });
  }
}
