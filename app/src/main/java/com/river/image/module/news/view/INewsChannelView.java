package com.river.image.module.news.view;

import com.river.image.base.BaseView;
import com.river.image.bean.NewsChannel;
import com.river.image.bean.NewsChannelBean;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
public interface INewsChannelView extends BaseView{
    void getDataByNet(NewsChannel newsChannel);
    void getDataByDB(List<NewsChannelBean> channelBean);
}
