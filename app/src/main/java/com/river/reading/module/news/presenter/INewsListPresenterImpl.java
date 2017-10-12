package com.river.reading.module.news.presenter;

import com.river.reading.base.BasePresenterImpl;
import com.river.reading.bean.NewsBean;
import com.river.reading.common.DataType;
import com.river.reading.http.ApiConfig;
import com.river.reading.module.news.model.INewsListModel;
import com.river.reading.module.news.model.INewsListModelImpl;
import com.river.reading.module.news.view.INewsListView;

/**
 * Created by Administrator on 2016/10/25.
 */

public class INewsListPresenterImpl extends BasePresenterImpl<INewsListView, NewsBean>
    implements INewsListPresenter {
  private INewsListModel<NewsBean> mINewsListModel = null;
  private String channelId;
  private String channelName;
  private int maxResult=20;
  private int page=1;
  private boolean isRefresh=true;
  public INewsListPresenterImpl(INewsListView view) {
    super(view);
    mINewsListModel = new INewsListModelImpl();
  }

  @Override public void refreshData() {
    page=1;
    maxResult=20;
    isRefresh=true;
    startLoadData(channelId, channelName,page);
  }

  @Override public void loadMoreData() {
    isRefresh=false;
    page++;
    startLoadData(channelId, channelName,page);
  }

  @Override public void startLoadData(String channelId, String channelName,int page) {
    this.channelId=channelId;
    this.channelName=channelName;
    mSubscription = mINewsListModel.requestNewsList(this, channelId, channelName, String.valueOf(maxResult),  "1", "1", "0", String.valueOf(page),
        ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void requestSuccess(NewsBean data) {
    mView.updateNewsList(data,"",isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_MORE_SUCCESS);
  }

  @Override public void requestError(String msg) {
    super.requestError(msg);
    mView.updateNewsList(null,msg,isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_MORE_SUCCESS);

  }
}
