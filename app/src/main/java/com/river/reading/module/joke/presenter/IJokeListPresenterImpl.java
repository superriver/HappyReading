package com.river.reading.module.joke.presenter;

import com.river.reading.base.BasePresenterImpl;
import com.river.reading.bean.JokeBean;
import com.river.reading.common.DataType;
import com.river.reading.http.ApiConfig;
import com.river.reading.module.joke.model.IJokeModel;
import com.river.reading.module.joke.model.IJokeModelImpl;
import com.river.reading.module.joke.view.IJokeListView;

/**
 * Created by Administrator on 2016/11/28.
 */

public class IJokeListPresenterImpl extends BasePresenterImpl<IJokeListView,JokeBean> implements
    IJokeListPresenter {
  private IJokeModel mJokeModel =null;
  private int page=1;
  private boolean isRefresh=true;
  private String dataType;
  public IJokeListPresenterImpl(IJokeListView jokeListView,String type) {
    super(jokeListView);
    mJokeModel=new IJokeModelImpl();
    startLoadData(type,page);
  }

  @Override public void startLoadData(String type,int page) {
    dataType=type;
    mSubscription=mJokeModel.requestJokeList(this, dataType, "20", String.valueOf(page), ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void refreshData() {
    page=1;
    isRefresh=true;
    startLoadData(dataType,page);
  }

  @Override public void loadMoreData() {
    isRefresh=false;
    page++;
    startLoadData(dataType,page);
  }

  @Override public void requestSuccess(JokeBean data) {
    mView.updateJokeList(data,"",isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_MORE_SUCCESS);
  }

  @Override public void requestError(String msg) {
    super.requestError(msg);
    mView.updateJokeList(null,msg,isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_MORE_SUCCESS);
  }
}
