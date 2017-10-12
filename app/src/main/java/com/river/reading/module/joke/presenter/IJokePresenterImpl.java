package com.river.reading.module.joke.presenter;

import com.river.reading.base.BasePresenterImpl;
import com.river.reading.module.joke.view.IJokeView;

/**
 * Created by Administrator on 2016/11/29.
 */

public class IJokePresenterImpl extends BasePresenterImpl<IJokeView,Void> implements IJokePresenter{
  public IJokePresenterImpl(IJokeView view) {
    super(view);
    view.initViewPager();
  }
}
