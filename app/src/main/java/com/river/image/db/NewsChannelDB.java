package com.river.image.db;

import com.river.image.MyApplication;
import com.river.image.bean.NewsChannel;
import com.river.image.bean.NewsChannelBean;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class NewsChannelDB implements Closeable{
  private static  Realm mRealm;
  public NewsChannelDB() {
      mRealm = Realm.getInstance(new RealmConfiguration.Builder(MyApplication.getInstance()).name("newsChannel.realm")
      .build());
  }
  //保存数据
  public static void insertDB(final NewsChannel newsChannel){
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
       NewsChannel.NewsChannelBody.ChannelListBean[] channelLists =
            newsChannel.res_body.channelList;
        for (NewsChannel.NewsChannelBody.ChannelListBean channelListBean : channelLists) {
          NewsChannelBean newsChannelBean = mRealm.createObject(NewsChannelBean.class);
          newsChannelBean.setChannelId(channelListBean.channelId);
          newsChannelBean.setName(channelListBean.name);
        }
      }
    });
  }
  //查找数据
  public  List<NewsChannelBean> selectDB(){
    List<NewsChannelBean> results = mRealm.where(NewsChannelBean.class).findAll();
    return results;
  }

  /**
   * Closes this stream and releases any system resources associated
   * with it. If the stream is already closed then invoking this
   * method has no effect.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override public void close() throws IOException {
    mRealm.close();
  }
}
