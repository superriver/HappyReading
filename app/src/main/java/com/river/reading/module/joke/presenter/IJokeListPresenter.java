package com.river.reading.module.joke.presenter;

import com.river.reading.base.BasePresenter;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface IJokeListPresenter extends BasePresenter{
  void startLoadData(String type,int page);
  void refreshData();
  void loadMoreData();
}
