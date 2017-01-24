package com.river.image.module.news.presenter;

import com.river.image.base.BasePresenterImpl;
import com.river.image.bean.NewsBean;
import com.river.image.http.ApiConfig;
import com.river.image.module.news.model.INewsListModel;
import com.river.image.module.news.model.INewsListModelImpl;
import com.river.image.module.news.view.INewsListView;

/**
 * Created by Administrator on 2016/10/25.
 */

public class INewsListPresenterImpl extends BasePresenterImpl<INewsListView, NewsBean>
    implements INewsListPresenter {
  private INewsListModel<NewsBean> mINewsListModel = null;
  private String channelId;
  private String channelName;
  private int maxResult=20;
  public INewsListPresenterImpl(INewsListView view) {
    super(view);
    mINewsListModel = new INewsListModelImpl();
  }

  @Override public void refreshData() {
    maxResult=0;
    startLoadData(channelId, channelName);
  }

  @Override public void loadMoreData() {
    //if (contentBean.size() % 20 == 0) {
    //  mPage++;
    //  maxSize+=20;
    //  mPresenter.startLoadData(mNewsChannelId, mNewsChannelName, maxSize, "1", "1", "0",
    //      String.valueOf(mPage), ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);
    //}
    startLoadData(channelId, channelName);
  }

  @Override public void startLoadData(String channelId, String channelName) {
    this.channelId=channelId;
    this.channelName=channelName;
    mINewsListModel.requestNewsList(this, channelId, channelName, String.valueOf(maxResult), "1", "1", "0", "1",
        ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void requestSuccess(NewsBean data) {
    if(null!=data){
      maxResult+=20;
    }
    mView.updateNewsList(data);
  }

  @Override public void requestError(String msg) {
    super.requestError(msg);
  }
}
