package com.river.reading.module.joke.view;

import com.river.reading.base.BaseView;
import com.river.reading.bean.JokeBean;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface IJokeListView extends BaseView{
  void updateJokeList(JokeBean jokeBean,String errorMsg,String type);
}
