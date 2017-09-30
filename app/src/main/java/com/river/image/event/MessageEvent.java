package com.river.image.event;

/**
 * Created by Administrator on 2017/1/17.
 */

public class MessageEvent<T> {
  private int position;
  private T data;

  public MessageEvent(int position, T data) {
    this.position = position;
    this.data = data;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
