package com.river.reading.module.news.presenter;

import com.river.reading.base.BasePresenter;

/**
 * Created by Administrator on 2016/10/17.
 */

public interface INewsChannelPresenter extends BasePresenter{
  void refreshData();
  void getData();
}
