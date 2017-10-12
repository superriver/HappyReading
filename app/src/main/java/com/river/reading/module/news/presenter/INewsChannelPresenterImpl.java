package com.river.reading.module.news.presenter;

import com.river.reading.base.BasePresenterImpl;
import com.river.reading.bean.NewsChannel;
import com.river.reading.bean.NewsChannelBean;
import com.river.reading.db.NewsChannelDB;
import com.river.reading.http.ApiConfig;
import com.river.reading.module.news.model.INewsChannelModel;
import com.river.reading.module.news.model.INewsChannelModelImpl;
import com.river.reading.module.news.view.INewsChannelView;
import com.socks.library.KLog;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

public class INewsChannelPresenterImpl extends BasePresenterImpl<INewsChannelView,NewsChannel>
    implements INewsChannelPresenter{
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
