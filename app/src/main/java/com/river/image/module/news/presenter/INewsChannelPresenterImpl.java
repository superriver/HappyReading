package com.river.image.module.news.presenter;

import com.river.image.base.BasePresenterImpl;
import com.river.image.bean.NewsChannel;
import com.river.image.bean.NewsChannelBean;
import com.river.image.db.NewsChannelDB;
import com.river.image.http.ApiConfig;
import com.river.image.module.news.model.INewsChannelModel;
import com.river.image.module.news.model.INewsChannelModelImpl;
import com.river.image.module.news.view.INewsChannelView;
import com.socks.library.KLog;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

public class INewsChannelPresenterImpl extends BasePresenterImpl<INewsChannelView,NewsChannel> implements INewsChannelPresenter{
  protected INewsChannelModel mINewsChannelModel;
  //private boolean isFirst=true;
  public INewsChannelPresenterImpl(INewsChannelView view) {
    super(view);
    mINewsChannelModel = new INewsChannelModelImpl();
    //try{
    //
    //}catch (IOException io){
    //  io.printStackTrace();
    //}
    NewsChannelDB newsChannelDB = new NewsChannelDB();
    List<NewsChannelBean> channel =  newsChannelDB.selectDB();
    if(channel.size()==0){
      KLog.d("TAG","INewsChannelPresenterImpl");
      mSubscription = mINewsChannelModel.requestNewsChannel(this, ApiConfig.SHOWAPI_APPID,null,ApiConfig.SHOWAPI_SIGN);
    }else {
      mView.getDataByDB(channel);
    }
  }

  //获取解析成功的数据，传给view层进行数据处理
  @Override public void requestSuccess(NewsChannel data) {
       mView.getDataByNet(data);

  }

  @Override public void refreshData() {
    // TODO: 2016/10/17  
  }

  @Override public void getData() {
    // TODO: 2016/10/17  
  }

}
