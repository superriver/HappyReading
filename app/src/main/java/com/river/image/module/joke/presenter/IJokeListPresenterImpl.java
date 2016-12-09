package com.river.image.module.joke.presenter;

import com.river.image.base.BasePresenterImpl;
import com.river.image.bean.TextJokeBean;
import com.river.image.module.joke.model.IJokeModel;
import com.river.image.module.joke.model.IJokeModelImpl;
import com.river.image.module.joke.view.IJokeListView;

/**
 * Created by Administrator on 2016/11/28.
 */

public class IJokeListPresenterImpl extends BasePresenterImpl<IJokeListView,TextJokeBean> implements
    IJokeListPresenter {
  private IJokeModel mJokeModel =null;

  public IJokeListPresenterImpl(IJokeListView jokeListView) {
    super(jokeListView);
    mJokeModel=new IJokeModelImpl();
  }

  @Override public void startLoadData(String type,String maxResult, String page, String appid, String timestamp,
      String showapi_sign) {
    mJokeModel.requestJokeList(this,type,maxResult,  page,  appid,  timestamp,
        showapi_sign);
  }

  @Override public void requestSuccess(TextJokeBean data) {
      mView.updateJokeList(data);
  }
}
