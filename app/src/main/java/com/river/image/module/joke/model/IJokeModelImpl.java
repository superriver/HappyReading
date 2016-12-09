package com.river.image.module.joke.model;

import com.river.image.bean.TextJokeBean;
import com.river.image.callback.RequestCallBack;
import com.river.image.http.HostType;
import com.river.image.http.RetrofitManager;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/11/28.
 */

public class IJokeModelImpl implements IJokeModel<TextJokeBean> {
  @Override
  public Subscription requestJokeList(RequestCallBack<TextJokeBean> callBack,String type, String maxResult, String page,
      String appid, String timestamp,String showapi_sign) {
    return RetrofitManager.getInstance(HostType.NEWS_HOST).getTextJokeList(type,maxResult,page,appid,timestamp,showapi_sign)
        .subscribe(new Subscriber<TextJokeBean>() {
          @Override public void onCompleted() {
//            KLog.d("TAG","Joke-->OnCompleted");
          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onNext(TextJokeBean textJokeBean) {
            callBack.requestSuccess(textJokeBean);
//            KLog.d("TAG","onNext-->"+textJokeBean.showapi_res_body.contentlist.size());
          }
        });
  }
}
