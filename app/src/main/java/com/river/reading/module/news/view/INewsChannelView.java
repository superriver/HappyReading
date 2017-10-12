package com.river.reading.module.news.view;

import com.river.reading.base.BaseView;
import com.river.reading.bean.NewsChannel;
import com.river.reading.bean.NewsChannelBean;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
public interface INewsChannelView extends BaseView {
    void getDataByNet(NewsChannel newsChannel);
    void getDataByDB(List<NewsChannelBean> channelBean);
}
