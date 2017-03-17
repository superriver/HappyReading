package com.river.image.module.joke.presenter;

import com.river.image.base.BasePresenterImpl;
import com.river.image.bean.JokeBean;
import com.river.image.common.DataType;
import com.river.image.http.ApiConfig;
import com.river.image.module.joke.model.IJokeModel;
import com.river.image.module.joke.model.IJokeModelImpl;
import com.river.image.module.joke.view.IJokeListView;
import com.socks.library.KLog;

/**
 * Created by Administrator on 2016/11/28.
 */

public class IJokeListPresenterImpl extends BasePresenterImpl<IJokeListView,JokeBean> implements
    IJokeListPresenter {
  private IJokeModel mJokeModel =null;
  private int maxResult=20;
  private int page=1;
  private boolean isRefresh=true;
  private String dataType;
  public IJokeListPresenterImpl(IJokeListView jokeListView,String type) {
    super(jokeListView);
    mJokeModel=new IJokeModelImpl();
    startLoadData(type);
  }

  @Override public void startLoadData(String type) {
    dataType=type;
    mSubscription=mJokeModel.requestJokeList(this, dataType, String.valueOf(maxResult), String.valueOf(page), ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void refreshData() {
    page=1;
    isRefresh=true;
    startLoadData(dataType);
  }

  @Override public void loadMoreData() {
    KLog.d("TAG","loadMoreData-》"+page);
    isRefresh=false;
    page++;
    startLoadData(dataType);
  }

  @Override public void requestSuccess(JokeBean data) {
    KLog.d("TAG","page-》"+page);
    mView.updateJokeList(data,isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_MORE_SUCCESS);
  }
}
