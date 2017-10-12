package com.river.reading.module.picture.model;

import com.river.reading.bean.GirlsBean;
import com.river.reading.callback.LoadImageCallback;
import com.river.reading.http.HostType;
import com.river.reading.http.RetrofitManager;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/9/9.
 */
public class IImageInteractorImpl implements IImageInteractor {

  @Override public Subscription requestImageInfo(int page,
      int size,final LoadImageCallback callback) {

    return RetrofitManager.getInstance(HostType.IMAGE_HOST).getImageInfoObservable("福利",size,page)
        .subscribe(new Subscriber<GirlsBean>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override public void onNext(GirlsBean girlsBean) {
                callback.getImageInfo(girlsBean);
          }
        });
  }
}
