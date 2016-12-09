package com.river.image.module.news.view;

import com.river.image.base.BaseView;
import com.river.image.bean.NewsBean;

/**
 * Created by Administrator on 2016/10/25.
 */

public interface INewsListView extends BaseView{
      void updateNewsList(NewsBean newsBean);
      //void runOnUiThread(Runnable runnable);
}
