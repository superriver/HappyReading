package com.river.image.base;

import com.river.image.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/17.
 */

public class BasePresenterImpl<T extends BaseView,V> implements BasePresenter, RequestCallBack<V>{
  protected T mView;
  protected Subscription mSubscription;
  public BasePresenterImpl(T view) {
    mView = view;
  }

  @Override public void requestBefore() {

  }

  @Override public void requestError(String msg) {

  }

  @Override public void requestComplete() {

  }

  @Override public void requestSuccess(V data) {

  }

  @Override public void onDestroy() {
      if(null!=mSubscription&&!mSubscription.isUnsubscribed()){
        mSubscription.unsubscribe();
      }
       mView=null;
  }
}
