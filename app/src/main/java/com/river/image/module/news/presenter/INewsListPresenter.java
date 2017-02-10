package com.river.image.module.news.presenter;

import com.river.image.base.BasePresenter;

/**
 * Created by Administrator on 2016/10/25.
 */

public interface INewsListPresenter extends BasePresenter{
  void refreshData();
  void loadMoreData();
  void startLoadData(String channelId, String channelName,int page);
}
