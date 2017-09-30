package com.river.image.module.joke.view;

import com.river.image.base.BaseView;
import com.river.image.bean.JokeBean;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface IJokeListView extends BaseView{
  void updateJokeList(JokeBean jokeBean,String errorMsg,String type);
}
