package com.river.image.module.news.presenter;

import com.river.image.base.BasePresenterImpl;
import com.river.image.bean.NewsBean;
import com.river.image.module.news.model.INewsListModel;
import com.river.image.module.news.model.INewsListModelImpl;
import com.river.image.module.news.view.INewsListView;

/**
 * Created by Administrator on 2016/10/25.
 */

public class INewsListPresenterImpl extends BasePresenterImpl<INewsListView, NewsBean>
    implements INewsListPresenter {
  private INewsListModel<NewsBean> mINewsListModel = null;

  public INewsListPresenterImpl(INewsListView view) {
    super(view);
    mINewsListModel = new INewsListModelImpl();
  }

  @Override public void refreshData() {

  }



  @Override public void startLoadData(String channelId, String channelName,
      String maxResult, String needAllList, String needContent, String needHtml, String page,
      String appid, String timestamp, String title, String showapi_sign) {
    mINewsListModel.requestNewsList(this, channelId, channelName, maxResult, needAllList,
        needContent, needHtml, page, appid, timestamp, title, showapi_sign);
  }

  @Override public void requestSuccess(NewsBean data) {

    mView.updateNewsList(data);
  }

  @Override public void requestError(String msg) {
    super.requestError(msg);
  }
}
