package com.river.image.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/8/25.
 */
public class NewsChannelBean extends RealmObject{
      private  String channelId;
      private String name;

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
