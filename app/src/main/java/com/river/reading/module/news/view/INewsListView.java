package com.river.reading.module.news.view;

import com.river.reading.base.BaseView;
import com.river.reading.bean.NewsBean;

/**
 * Created by Administrator on 2016/10/25.
 */

public interface INewsListView extends BaseView {
      void updateNewsList(NewsBean newsBean,String errorMsg,String type);
      //void runOnUiThread(Runnable runnable);
}
