package com.river.image.module.picture.presenter;

import com.river.image.bean.GirlsBean;
import com.river.image.callback.LoadImageCallback;
import com.river.image.module.picture.ImageContract;
import com.river.image.module.picture.model.IImageInteractor;
import com.river.image.module.picture.model.IImageInteractorImpl;

/**
 * Created by Administrator on 2016/9/9.
 */
public class IImagePresenterImpl implements ImageContract.Presenter{
  private ImageContract.View mView;
  private IImageInteractor mInteractor;

  public IImagePresenterImpl(ImageContract.View view) {
    mView = view;
    mInteractor = new IImageInteractorImpl();
  }

  @Override public void getGirls(int page, int size, final boolean isRefresh) {
    mInteractor.requestImageInfo(page, size, new LoadImageCallback() {
      @Override public void getImageInfo(GirlsBean girlsBean) {
        if(isRefresh){
         // KLog.d("TAG","Presenter1"+girlsBean.toString());
          mView.refresh(girlsBean.getResults());
        }else{
         // KLog.d("TAG","Presenter2"+girlsBean.toString());
          mView.load(girlsBean.getResults());
        }
      }
    });
  }

  @Override public void onDestroy() {

  }
}
