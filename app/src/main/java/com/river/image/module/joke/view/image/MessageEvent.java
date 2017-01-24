package com.river.image.module.joke.view.image;

import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import java.util.List;
/**
 * Created by Administrator on 2017/1/17.
 */

public class MessageEvent {
  private int position;
  private List<ContentlistBean> data;

  public MessageEvent(int position, List<ContentlistBean> data) {
    this.position = position;
    this.data = data;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public List<ContentlistBean> getData() {
    return data;
  }

  public void setData(List<ContentlistBean> data) {
    this.data = data;
  }
}
