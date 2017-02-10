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
  private boolean isRefresh;
  private String dataType;
  public IJokeListPresenterImpl(IJokeListView jokeListView) {
    super(jokeListView);
    mJokeModel=new IJokeModelImpl();
  }

  @Override public void startLoadData(String type) {
    dataType=type;
    mJokeModel.requestJokeList(this, dataType, String.valueOf(maxResult), String.valueOf(page), ApiConfig.SHOWAPI_APPID, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void refreshData() {
    page=1;
    isRefresh=true;
    startLoadData(dataType);
  }

  @Override public void loadMoreData() {
    isRefresh=false;
    page++;
    startLoadData(dataType);
  }

  @Override public void requestSuccess(JokeBean data) {
    //if(null!=data){
    //  //maxResult+=20;
    //  page++;
    //}
    KLog.d("TAG","page-》"+page);
    mView.updateJokeList(data,isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_SUCCESS);
  }
}
