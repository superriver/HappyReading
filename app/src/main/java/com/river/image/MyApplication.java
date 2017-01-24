package com.river.image;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/9/26.
 */

public class MyApplication extends Application {
  private static MyApplication mApplication;
  private static Context mContext;
  @Override public void onCreate() {
    super.onCreate();
    mApplication = this;
    mContext=this;
  }
  public static Context getContext(){
    return mContext;
  }
  public static MyApplication getInstance(){
    return  mApplication;
  }
}
