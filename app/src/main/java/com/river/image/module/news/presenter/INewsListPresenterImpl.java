package com.river.image.module.news.presenter;

import com.river.image.base.BasePresenterImpl;
import com.river.image.bean.NewsBean;
import com.river.image.common.DataType;
import com.river.image.http.ApiConfig;
import com.river.image.module.news.model.INewsListModel;
import com.river.image.module.news.model.INewsListModelImpl;
import com.river.image.module.news.view.INewsListView;
import com.socks.library.KLog;

/**
 * Created by Administrator on 2016/10/25.
 */

public class INewsListPresenterImpl extends BasePresenterImpl<INewsListView, NewsBean>
    implements INewsListPresenter {
  private INewsListModel<NewsBean> mINewsListModel = null;
  private String channelId;
  private String channelName;
  private int maxResult;
  private int page=1;
  private boolean isRefresh=true;
  public INewsListPresenterImpl(INewsListView view) {
    super(view);
    mINewsListModel = new INewsListModelImpl();
    maxResult=20;
  }

  @Override public void refreshData() {
    page=1;
    isRefresh=true;

    startLoadData(channelId, channelName);
  }

  @Override public void loadMoreData() {
    isRefresh=false;
    KLog.d("TAG","page->"+page);
    startLoadData(channelId, channelName);
  }

  @Override public void startLoadData(String channelId, String channelName) {
    KLog.d("TAG","page->"+page);
    this.channelId=channelId;
    this.channelName=channelName;
    mINewsListModel.requestNewsList(this, channelId, channelName, String.valueOf(maxResult), String.valueOf(page), "1", "0", "1",
        ApiConfig.SHOWAPI_APPID, null, null, ApiConfig.SHOWAPI_SIGN);
  }

  @Override public void requestSuccess(NewsBean data) {
    KLog.d("TAG","requestSuccess->"+page);
    if(null!=data){
      //page++;
      maxResult+=20;
    }
    mView.updateNewsList(data,isRefresh? DataType.DATA_REFRESH_SUCCESS:DataType.DATA_LOAD_SUCCESS);
  }

  @Override public void requestError(String msg) {
    super.requestError(msg);
  }
}
