package com.river.image.module.joke.view;

import com.river.image.base.BaseView;
import com.river.image.bean.TextJokeBean;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface IJokeListView extends BaseView{
  void updateJokeList(TextJokeBean jokeBean);
}
