package com.river.reading.module.news.presenter;

import com.river.reading.base.BasePresenter;

/**
 * Created by Administrator on 2016/10/25.
 */

public interface INewsListPresenter extends BasePresenter{
  void refreshData();
  void loadMoreData();
  void startLoadData(String channelId, String channelName,int page);
}
